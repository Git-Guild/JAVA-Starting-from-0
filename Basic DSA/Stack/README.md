# Stack

A **stack** is a data structure where you can only touch the **top** — like a pile of plates. You add a plate on top and you take a plate off the top. You never pull one out of the middle.

```
                 ┌─────────┐
   push(19) ───▶ │   19    │  ← top (the only place you can touch)
                 ├─────────┤
                 │   25    │
                 ├─────────┤
                 │   15    │
                 ├─────────┤
                 │   10    │
                 ├─────────┤
                 │    5    │
                 └─────────┘
                      ▲
                    bottom
```

That rule has a name: **LIFO — Last In, First Out**. The last thing you pushed is the first thing you pop. A queue is the opposite (FIFO — first in, first out).

```
   push 5 → [5]
   push 10 → [5, 10]
   push 15 → [5, 10, 15]
   pop    → returns 15      ← the LAST one in came out FIRST
   pop    → returns 10
```

## Why Do We Need a Stack?

Because a lot of problems are naturally "most recent thing first":

- **Undo (Ctrl+Z)** — the last edit is the first one undone.
- **Browser back button** — the last page visited is the first one you go back to.
- **Matching brackets** — `( [ { } ] )` — you close the *most recently opened* bracket first.
- **Expression evaluation / calculators** — `2 + 3 * 4`, operator precedence is a stack job.
- **Function calls** — Java's own call stack is a stack! That is where `StackOverflowError` comes from (see the [Recursion](../Recursion/README.md) folder).

The important part: if a problem says *"remember the most recent thing"*, it is probably a stack.

## The Three Operations

Every stack, no matter how it is built, only needs three moves:

```
   push(x)   add x on top
   pop()     remove and return the top
   peek()    look at the top WITHOUT removing it
   isEmpty() is there anything left?
```

Everything else — `size`, `display` — is built on top of these.

## Table of Contents

- [Why Do We Need a Stack?](#why-do-we-need-a-stack)
- [The Three Operations](#the-three-operations)
- [File 1 — `Basics.java`: a stack from scratch](#file-1--basicsjava-a-stack-from-scratch)
  - [The `top` pointer](#the-top-pointer)
  - [Overflow and underflow](#overflow-and-underflow)
- [File 2 — `PreBuilt.java`: a stack with `Deque`](#file-2--prebuiltjava-a-stack-with-deque)
  - [What is a Deque?](#what-is-a-deque)
  - [Why not `java.util.Stack`?](#why-not-javautilstack)
- [From Scratch vs `Deque`](#from-scratch-vs-deque)
- [Cost at a Glance](#cost-at-a-glance)
- [Files](#files)
- [Output](#output)
- [Common Mistakes](#common-mistakes)
- [Next Steps](#next-steps)

---

## File 1 — `Basics.java`: a stack from scratch

This file builds a working stack out of **nothing but an array and one integer**. No library. This is the file to read first, because it shows what is actually happening inside the data structure.

### The two ingredients

```java
class Stack {
    private int size;          // how many slots the stack can hold
    private int top = -1;      // index of the current top element
    private int[] stack;       // the actual storage
}
```

- **`stack`** is a plain `int[]` — the shelf.
- **`size`** is the capacity — how many slots the shelf has.
- **`top`** is the *index* of the newest element.

Why does `top` start at `-1`? Because an index of `-1` means **"no valid element"** — the stack is empty. It is not `0`, because `0` is a real index that would say "there is one element in slot 0".

```
   empty              after push(5)        after push(10)       after push(15)
   top = -1           top = 0             top = 1              top = 2

   +----+----+        +----+----+         +----+----+          +----+----+
   |    |    |        |  5 |    |         |  5 | 10 |          |  5 | 10 |   ...
   +----+----+        +----+----+         +----+----+          +----+----+
    0    1             0    1              0    1               0    1
                        ▲                  ▲                    ▲
                       top                top                  top
```

### `setSize()` — deciding the shelf size once

```java
public void setSize() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter size of stack: ");
    size = scanner.nextInt();
    stack = new int[size];
}
```

The array is created **after** the user types a number, because `new int[size]` needs `size` to already exist. This is also the file's big limitation: once created, the stack can never grow. That is exactly why `PreBuilt.java` uses a `Deque` later.

### `push()` — move `top` up, then write

```java
public void push(int val) {
    if (top >= size - 1) {                    // overflow check
        System.out.println("OVERFLOW: Cannot push " + val);
    } else {
        top++;                                // step up to the next free slot
        stack[top] = val;                     // then write into it
    }
}
```

The order matters — **increment first, write second**. Two separate steps, never `stack[top++] = val` style confusion:

```
before push(19):   top = 3        stack = [5][10][15][25][ ][ ]
                                   0   1   2   3  4  5

top++          →   top = 4
stack[top] = 19 →  stack = [5][10][15][25][19][ ]
                                           ▲
                                          top
```

### `pop()` — read, then move `top` down

```java
public void pop() {
    if (top == -1) {                          // underflow check
        System.out.println("UNDERFLOW: Stack is empty");
    } else {
        System.out.println("Popped " + stack[top]);  // read first
        top--;                                       // then step down
    }
}
```

Notice `pop()` here **prints** the value instead of returning it. If you want the value back, change the method to `public int pop()` and `return stack[top--];`. The old value is still sitting in the array — but `top` no longer points at it, so logically it is gone. The next `push` will simply overwrite it.

### `display()` — walk down from `top`

```java
for (int i = top; i >= 0; i--) {
    System.out.print(stack[i] + " ");
}
```

A stack can only be read from the top, so this loop starts at `top` and counts **down** to `0`. That is why the output reads `Top -> Bottom`.

### Overflow and underflow

These are the two ways a stack can break, and the two `if` checks above are the fix:

```
   OVERFLOW                          UNDERFLOW
   pushing into a FULL stack         popping from an EMPTY stack

   size = 5, top = 4 (last slot)     top = -1
   +----+----+----+----+----+        +----+----+
   |  5 | 10 | 15 | 25 | 19 |        |    |    |
   +----+----+----+----+----+        +----+----+
                          ▲             ▲
                         top           top
   push(9) → no room!                pop() → nothing to remove
   "OVERFLOW: Cannot push 9"         "UNDERFLOW: Stack is empty"
```

In `main`, the user is asked for a size, and then six values are pushed: `5, 10, 15, 25, 19, 9`. So:

- size **6 or more** → all six go in.
- size **5 or less** → the last push (`9`) overflows and is refused.

Try both, and watch exactly which push gets rejected.

> **Note on `main`:** this file uses Java's newer simplified entry point — `void main()` without `public static`, and the class-free syntax. Run it with a recent JDK via `java Basics.java`. On older JDKs, wrap it in `public static void main(String[] args)` inside a class.

---

## File 2 — `PreBuilt.java`: a stack with `Deque`

Writing the array stack by hand teaches you the mechanics — but in real code you do not build it by hand. Java already has one. This file is the **practical, everyday version**, and it is only 20 lines because the data structure does all the work.

```java
import java.util.ArrayDeque;
import java.util.Deque;

Deque<Integer> stack = new ArrayDeque<>();
stack.push(5);
stack.push(10);
stack.push(15);
stack.push(25);
stack.push(19);

System.out.println("Total elements in stack: " + stack.size());
System.out.println("Element at the top: " + stack.peek());

stack.pop();
System.out.println("New element at the top: " + stack.peek());
```

Compare the method names with the from-scratch file — they are the *same ideas*, just spelled by the library:

| From scratch (`Basics.java`) | `Deque` (`PreBuilt.java`) | What it does |
|---|---|---|
| `push(val)` | `stack.push(val)` | add to top |
| `pop()` | `stack.pop()` | remove and **return** the top |
| `display()` reads `stack[top]` | `stack.peek()` | look at the top without removing |
| `top == -1` | `stack.isEmpty()` | is it empty? |
| `size` field | `stack.size()` | how many elements |
| `OVERFLOW` check | not needed — it grows by itself | |

So the whole from-scratch file becomes five library calls. That is not cheating — it is the point of data structures: understand the machine once, then use the safe built version forever.

### What is a Deque?

**Deque** is pronounced *"deck"* and stands for **D**ouble-**E**nded **QUE**ue — a queue you can add to and remove from **both ends**.

```
   Deque = both ends open

        push / pop                  push / pop
            │                          │
            ▼                          ▼
        ┌───────┬───────┬───────┬───────┐
        │       │       │       │       │
        └───────┴───────┴───────┴───────┘
            ▲                          ▲
            │                          │
     offerFirst / pollFirst    offerLast / pollLast     ← queue-style (FIFO)
```

Because both ends are open, a `Deque` can act as **both** a stack and a queue:

```
   Use it as a STACK (LIFO)         Use it as a QUEUE (FIFO)
   ────────────────────────         ───────────────────────
   push(x)  → add at front          offerLast(x) → add at back
   pop()    → remove from front     pollFirst()  → remove from front
   peek()   → look at front         peekFirst()  → look at front
```

`PreBuilt.java` uses the **first three** — `push`, `pop`, `peek` — so it behaves exactly like a stack. The comment at the top of the file says this too: *"Deque can be both LIFO and FIFO, we will use push() and pop() for LIFO Stack."*

Pick the methods by the behaviour you want, and the same `ArrayDeque` object does the job. Just do not mix them up: `push()` adds to the front, so `offerLast()` and `push()` do **not** mean the same thing.

### Why not `java.util.Stack`?

Java *does* have a class literally called `Stack`. Do not use it. It is a legacy class from Java 1.0, it extends `Vector`, and every method is `synchronized` — a lock on every call, which is slow and unnecessary for normal single-threaded code. The Java docs themselves recommend `Deque` instead.

```
   ❌ Stack<Integer> s = new Stack<>();        legacy, slow, synchronized
   ✅ Deque<Integer> s = new ArrayDeque<>();   modern, fast, recommended
```

`ArrayDeque` is also a growable array under the hood — the same idea as `Basics.java`, but with a real implementation of resizing, bounds checks, and a circular buffer. Nothing to write, nothing to overflow.

---

## From Scratch vs `Deque`

```
   Basics.java (array)                 PreBuilt.java (Deque)
   ─────────────────────               ─────────────────────────
   you own the array                   library owns the array
   you own `top`                       library tracks the position
   fixed size — can overflow           grows automatically
   you write the checks                built-in checks
   great for LEARNING                  great for SHIPPING
   push O(1), pop O(1)                 push O(1)*, pop O(1)*

   * amortized — a rare internal resize is spread across many calls
```

**One-line rule:** build it once by hand to understand it, then use `ArrayDeque` in every real project.

## Cost at a Glance

`█` ≈ one unit of work — longer bar = slower

```
                        Array stack (Basics)      ArrayDeque (PreBuilt)
push                    ██  O(1)                  ██  O(1) *
pop                     ██  O(1)                  ██  O(1) *
peek / top              ██  O(1)                  ██  O(1)
search for a value      ██████████  O(n)           ██████████  O(n)
grow beyond capacity    impossible — OVERFLOW      automatic copy, O(n) rare
extra memory            exactly `size` ints       a little bookkeeping
```

A stack is **fast at the top and blind everywhere else**. It never has to shift elements around — unlike an array insert at index `0`, which moves everything. That is the whole reason a stack is O(1) for its main operations.

## Files

Both `.java` files are self-contained and run on their own. **Read `Basics.java` first** — it is the "how it works" file. `PreBuilt.java` is the "how you actually use it" file, and it will make much more sense after the first.

| # | File | Teaches | Cost |
|---|------|---------|------|
| 1 | [`Basics.java`](Basics.java) | A stack built from scratch: the `int[]` array, the `top = -1` sentinel, the **increment-then-write** order in `push`, **overflow** and **underflow** guards, and reading top-to-bottom in `display` | push/pop O(1) |
| 2 | [`PreBuilt.java`](PreBuilt.java) | The library version: `Deque`/`ArrayDeque`, `push` / `pop` / `peek` / `size` / `isEmpty`, what a **double-ended queue** is, and why `java.util.Stack` is avoided | push/pop O(1) amortized |

**The mapping to keep in mind:** every method in `Basics.java` has a one-line twin in `PreBuilt.java`. Once you can see that, the library stops being magic.

> Both files use Java's newer simplified entry point — `void main()` without `public static`, and the class-free syntax in `PreBuilt.java`. Run them with a recent JDK via `java Basics.java` and `java PreBuilt.java`. On older JDKs, wrap them in a class with `public static void main(String[] args)`.

## Output

Real console output of each file.

**`Basics.java`** — input size `5`. Six values are pushed, so the sixth one hits overflow:
```
Enter size of stack: 5
Pushed 5
Pushed 10
Pushed 15
Pushed 25
Pushed 19
OVERFLOW: Cannot push 9
Stack elements (Top -> Bottom): 19 25 15 10 5
Popped 19
Stack elements (Top -> Bottom): 25 15 10 5
```

Same file, input size `6` — now everything fits:
```
Enter size of stack: 6
Pushed 5
Pushed 10
Pushed 15
Pushed 25
Pushed 19
Pushed 9
Stack elements (Top -> Bottom): 9 19 25 15 10 5
Popped 9
Stack elements (Top -> Bottom): 19 25 15 10 5
```

Same file, input size `0` — the first push already overflows, and `display` shows an empty stack:
```
Enter size of stack: 0
OVERFLOW: Cannot push 5
OVERFLOW: Cannot push 10
...
Stack is empty
UNDERFLOW: Stack is empty
UNDERFLOW: Stack is empty
```

**`PreBuilt.java`** — no input; it runs straight through:
```
Total elements in stack: 5
Element at the top: 19
Popping the top element...
New element at the top: 25
Remaining elements (Top -> Bottom):
25 15 10 5
```

Notice the final loop pops until `isEmpty()` — so the printed order `25 15 10 5` **is** the top-to-bottom order. Popping a stack always prints it in order.

## Common Mistakes

```
❌ starting top at 0 instead of -1     → the first element looks like "empty", size is off by one
❌ stack[top++] = val vs top++; stack[top] = val   → get the order wrong and you skip a slot
❌ forgetting the overflow check       → ArrayIndexOutOfBoundsException
❌ forgetting the underflow check      → reads stack[-1] → ArrayIndexOutOfBoundsException
❌ using java.util.Stack               → legacy and synchronized; use ArrayDeque
❌ mixing push() with offerLast()      → the two ends get confused, order breaks
❌ expecting to remove the middle      → a stack only exposes the top — that's the design
```

Debug tip: print `top` before and after every `push`/`pop`. If `top` ever goes below `-1` or above `size - 1`, you found the bug in one try.

## Next Steps

Once these are comfortable: **implement a stack with a linked list** (no fixed size — the same nodes you saw in [Linked List](../Linked%20List/README.md)), then solve the classic stack problems:

- **Balanced brackets** — push openers, pop on closers, check what comes back.
- **Reverse a string** — push every character, pop them all.
- **Evaluate a postfix expression** — `2 3 + 4 *` is a stack walk.
- **Next greater element** — the "monotonic stack" pattern.
- **Min stack** — keep a second stack of the running minimum.

Finally, connect it back to [Recursion](../Recursion/README.md): every recursive call is a `push`, every `return` is a `pop`. That is literally the call stack you have been using all along.
