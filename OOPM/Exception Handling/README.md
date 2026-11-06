# Exception Handling

The most Java-only part of error handling: a real class hierarchy, exceptions the
compiler *forces* you to deal with, and `finally` — the cleanup tool Java needs
because it has no destructors.

## What to know first

- Finish [`../Phase 1/`](../Phase%201/README.md) at minimum — the code here
  only assumes classes, methods, and objects; `try`/`catch` itself is taught
  in the theory below.
- [`../How Java Works.md`](../How%20Java%20Works.md) §4: why Java has no
  destructors/RAII — it's the reason `finally` exists.

## What this folder has

| File | What it teaches |
|------|-----------------|
| [`TryAndCatch.java`](TryAndCatch.java) | the basics: exception objects, `try`/`catch`, stack traces, catching by type |
| [`Error_Handling.java`](Error_Handling.java) | full demo: hierarchy, checked vs unchecked, `throw`/`throws`, custom exceptions, `finally`, multi-catch, try-with-resources |
| [`BuiltInExceptions.md`](BuiltInExceptions.md) | catalog of every built-in exception worth knowing, grouped by the `Throwable` tree |
| `README.md` | this file — the theory behind the code above |

Run the two programs straight from source (each file is named after its
`public` class):

```
java "Exception Handling/TryAndCatch.java"
java "Exception Handling/Error_Handling.java"
```

## Theory

### The keywords
- **`try`** — the block whose code might fail.
- **`throw`** — launches an exception **object**: `throw new MyException("msg")`.
  (C++ could throw bare ints/strings; Java always throws an object.)
- **`catch`** — intercepts a thrown exception **by its type**.
- **`finally`** — cleanup block that runs **no matter what** (below).
- **`throws`** — Java-only: a promise on a *method signature* that the method
  may throw something (required for checked exceptions).

```java
try {
    // risky work
} catch (IllegalStateException e) {
    // handle it: e.getMessage(), e.printStackTrace()
} finally {
    // runs on success, on failure, and even after `return` inside try
}
```

### The Throwable hierarchy
Everything thrown descends from `java.lang.Throwable`:

```
Throwable
├── Error                       DO NOT CATCH — JVM/system problems
│   ├── VirtualMachineError
│   │   ├── OutOfMemoryError      (heap exhausted)
│   │   └── StackOverflowError    (infinite recursion)
│   ├── AssertionError            (failed `assert`)
│   └── LinkageError
└── Exception                   things your program can handle
    ├── RuntimeException         UNCHECKED — logic bugs you should fix
    │   ├── NullPointerException        used `.` on null
    │   ├── ArithmeticException         e.g. / by 0
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── ClassCastException          bad downcast
    │   ├── IllegalArgumentException    bad argument
    │   │   └── NumberFormatException   ("abc" → int)
    │   └── IllegalStateException
    ├── IOException              CHECKED — file/network trouble
    ├── SQLException, InterruptedException, ClassNotFoundException
    └── (your own: extends Exception = checked, extends RuntimeException = unchecked)
```

The complete catalog of built-in exceptions — every common one, grouped by
this tree with a "thrown when" explanation — is in
[`BuiltInExceptions.md`](BuiltInExceptions.md).

C++ mapping: `std::exception` → `Exception`, `std::logic_error` /
`invalid_argument` → `IllegalArgumentException`/`IllegalStateException`,
`std::out_of_range` → `IndexOutOfBoundsException`, `std::bad_alloc` →
`OutOfMemoryError`, `.what()` → `.getMessage()`.

### Checked vs unchecked (Java-only)
- **Unchecked** = `RuntimeException` + subclasses + `Error`. The compiler
  doesn't care whether you catch them — they're treated as bugs to fix.
- **Checked** = everything else under `Exception`. The compiler **forces** you
  to either `catch` it or declare `throws` on the method — so the decision is
  always made somewhere, never ignored. C++ has no equivalent: every C++
  exception is "unchecked".

```java
void readFile(String path) throws IOException { ... }   // push the decision up
try { readFile("missing.txt"); }
catch (IOException e) { System.out.println("file missing"); }
```

### finally — and why Java needs it
`finally` runs on **every** path out of try/catch: success, exception, `break`,
`continue`, even `return` (it runs *before* the method actually returns).
It's skipped only by `System.exit()`, a JVM crash, or a killed thread.

Why it exists: Java has **no destructors, no RAII**. The GC frees *memory*
whenever it likes, but files, sockets, and locks must be closed
*deterministically* — that's `finally`'s job.

**Modern preference — try-with-resources (Java 7+):** any `AutoCloseable`
declared in `try(...)` closes automatically in reverse order, even on exception:

```java
try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
    System.out.println(br.readLine());
}   // br.close() guaranteed here
```

### Common scenarios
- Division by zero → `ArithmeticException`
- Error 404 → `FileNotFoundException`, or a custom
  `ResourceNotFoundException extends IOException`
- Error 403 → custom checked exception, e.g. `AccessDeniedException`
- Error 402 → custom domain exception (`InsufficientFundsException` — full
  implementation in `Error_Handling.java`, carrying the deficit as data)
- `OutOfMemoryError` → heap can't grow (usually references held forever —
  Java can't free what you still point at)

### Best practices
- **Order catch blocks specific → broad** (`FileNotFoundException` before
  `IOException` before `Exception`) — Java checks top-down and rejects the
  wrong order with a compile error. Same rule as C++.
- **Never catch `Throwable`/`Error`** — the JVM itself is failing; you can't
  recover from that.
- **Never swallow** an empty `catch (Exception e) { }` hides bugs forever; at
  least log or rethrow.
- **Cleanup in `finally` or try-with-resources** — never only inside `try`
  (skipped exactly when something fails).
- **Custom exceptions:** `extends Exception` when callers must handle it,
  `extends RuntimeException` for programming errors.
- **Keep the cause:** `throw new AppException("failed", e)` preserves the
  original stack trace.

## What to do next

- Build a small project with real error handling: a grade book, a login form,
  or a file reader — using everything from all four phases, with proper
  exception handling this time.
- Then continue outside OOPM: [`../../Basic DSA/`](../../Basic%20DSA/README.md)
  (heap objects and references in earnest) or `Learning/Javac 25/` (records and
  modern style in production form).
