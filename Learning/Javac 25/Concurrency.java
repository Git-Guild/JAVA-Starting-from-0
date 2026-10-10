//* A thread or a worker is nothing but a process your CPU can run. Its speed depends on the core of your CPU and how well we optimize our code.
//& Platform threads are heavy (OS-level); a few thousand max. They are very heavy and expensive.
//* Virtual threads cost almost nothing; you can have a million. Great for waiting jobs (I/O).
//! "throws InterruptedException" warns that this method can be stopped from outside.

String slowTask(int id) throws InterruptedException {
    Thread.sleep(Duration.ofMillis(300)); //& Wait 0.3s
    //? Short if/else: virtual vs normal thread using a ternary operator '?' if true first one will execute otherwise the second
    String kind = Thread.currentThread().isVirtual() ? "a VIRTUAL thread" : "a normal thread";
    return "task " + id + " finished on " + kind;
}

//* Scoped value: a thread-safe noticeboard. Pin once, read anywhere down the stack but not change it.
//! Need: Imagine a job passes through many methods, and each one needs to know who is logged in? We can use a user parameter to see that but it will not be a good way to minimize space optimization.
//& static final is the standard pattern.
static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();

void handleRequest() {
    IO.println("  handleRequest reads the board: " + CURRENT_USER.get());
    audit();
}

void audit() {
    IO.println("  audit reads the board: " + CURRENT_USER.get());
} // 2 methods deep our way still works even with no parameter passed

//& throws Exception covers all possible problems here.
void main() throws Exception {

    IO.println("[1] One virtual thread");
    // The () -> ... lambda is the job. join() pauses until it finishes.
    Thread worker = Thread.ofVirtual().name("my-virtual-thread").start(
            () -> IO.println("Hello from " + Thread.currentThread()));
    worker.join(); 

    //! Output breakdown: 
    //* #27 is immutable thread ID (random). my-virtual-thread is mutable name.
    //? /runnable is state.
    //& Carrier Threads: Executes this virtual thread by temporarily mounting it onto a platform thread pool(ForkJoinPool), freeing the OS thread during blocking I/O.

/* 
    * An executor is a manager: you hand it jobs, it runs them for you.
     This kind gives every job its own virtual thread.
     submit(...) gives back a Future - a ticket for the answer.
     The job may not be done yet, but later ticket.get() waits and hands it over.
     try ( ... ) closes whatever is in the brackets when the block ends.
     For an executor that means: wait until every job is done.
     So the code after the block only runs when all 1000 jobs are finished.
     `final int id = i` copies i so the copy never changes. Each lambda needs its own copy, because i keeps changing round the loop.
*/
    IO.println("\n[2] A thousand virtual threads at once");
    long start = System.nanoTime();
    var tickets = new ArrayList<Future<String>>();
    
    // try-with-resources waits for all 1000 tasks to finish.
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 1; i <= 1000; i++) {
            final int id = i; //& Copy i so lambda closure gets an unchanging value.
            tickets.add(executor.submit(() -> slowTask(id))); //hand the job to executor
        }
    }
    
    long milliseconds = (System.nanoTime() - start) / 1_000_000;
    IO.println(tickets.getFirst().get());  // now the ticket access the slowtaska nd print 1 is virtual
    IO.println(tickets.getLast().get()); //same but it egts the last one which is 1000 job
    IO.println("All 1000 jobs took about " + milliseconds + " ms");

    IO.println("\n[3] Scoped values");
    IO.println("Is a user pinned now? " + CURRENT_USER.isBound()); // false
    
    //& where(...) pins the note for the run block.
    ScopedValue.where(CURRENT_USER, "violet").run(() -> {
        IO.println("Inside the scope:");
        handleRequest();
    });

    //& Nested scope temporarily overrides, then restores outer value.
    ScopedValue.where(CURRENT_USER, "alice").run(() -> {
        IO.println("Outer user: " + CURRENT_USER.get());
        ScopedValue.where(CURRENT_USER, "admin").run(() ->
                IO.println("  Inner user: " + CURRENT_USER.get()));
        IO.println("Back to outer: " + CURRENT_USER.get());
    });

    //& use call(...) instead of run(...) when you want a result back.
    String greeting = ScopedValue.where(CURRENT_USER, "bob")
            .call(() -> "Hello, " + CURRENT_USER.get() + "!");
    IO.println(greeting);
    IO.println("Is a user pinned now? " + CURRENT_USER.isBound()); // false

    //! Structured concurrency (Java preview feature - needs --enable-preview --source 25):
    // Treats related threads as one job; if one fails, others auto-stop.
    //     try (var scope = StructuredTaskScope.open()) {
    //         var user  = scope.fork(() -> fetchUser());
    //         var order = scope.fork(() -> fetchOrder());
    //         scope.join();
    //         IO.println(user.get() + " / " + order.get());
    //     }

    IO.println("\nDone. Next: Java25Tour.java");
}