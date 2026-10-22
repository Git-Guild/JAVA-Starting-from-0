# Recursion

**Recursion** is a function that calls *itself* to solve a smaller version of the same problem. Instead of writing the whole solution in one go, you solve one tiny piece and hand the rest to another copy of yourself.

```
factorial(4)  =  4 * factorial(3)
                     =  4 * 3 * factorial(2)
                             =  4 * 3 * 2 * factorial(1)
                                         =  4 * 3 * 2 * 1   ← base case stops it
                                         =  24
```

Every recursive function is just two ideas holding hands:

- **Base case** — the smallest input where you already know the answer. This is the *brake*.
- **Recursive case** — shrink the problem and call yourself. This is the *engine*.

No base case → the function never stops → `StackOverflowError`.

## Table of Contents

- [Why Do We Need Recursion?](#why-do-we-need-recursion)
- [The Two Rules](#the-two-rules)
- [The Call Stack](#the-call-stack)
- [Recursion vs Loops](#recursion-vs-loops)
- [Cost at a Glance](#cost-at-a-glance)
- [Files](#files)
- [Output](#output)
- [Common Mistakes](#common-mistakes)
- [Next Steps](#next-steps)

---

## Why Do We Need Recursion?

A loop is great when you know *how many* levels of work you need. But some problems have an **unknown number of nested steps** — and you cannot write an unknown number of `for` loops.

Look at generating combinations of length `k` from `"12345"`:

```
k = 2  →  two nested loops
          for (i = 0 …)
              for (j = i+1 …)          fine

k = 3  →  three nested loops
          for (i = 0 …)
              for (j = i+1 …)
                  for (l = j+1 …)      getting ugly

k = 10 →  ten nested loops?!
          you cannot write this by hand
```

Recursion is the answer: **one function = one loop level**. Each call goes one level deeper and picks the next character, so the *same code* handles `k = 2`, `k = 3`, or `k = 10`.

```
loop inside loop inside loop inside loop …
             │
             ▼
    one function that calls itself
    (the loops become the call stack)
```

Recursion isn't only for combinations. The same pattern solves:

- **Factorial** — `n! = n × (n-1)!`
- **Fibonacci** — `fib(n) = fib(n-1) + fib(n-2)`
- **Combinations / subsets / permutations** — pick one, explore, undo, pick the next
- **Tower of Hanoi** — move `n-1` disks out of the way, move the biggest, move them back
- **Trees and graphs** — every child is a smaller tree

---

## The Two Rules

### 1. Always have a base case

```
   factorial(1) → return 1        ✅ stops

   factorial(n) → n * factorial(n-1), with NO base case
                → calls forever  ❌ StackOverflowError
```

### 2. Every call must move *towards* the base case

```java
factorial(n - 1)   // ✅ smaller input, will reach 1
factorial(n + 1)   // ❌ bigger input, never reached
factorial(n)       // ❌ same input, calls itself forever
```

Base case = *where to stop*. Shrinking input = *how to get there*. Both, or you crash.

---

## The Call Stack

Java does not run the calls side by side — it stacks them. Each call **pauses** and waits for the one below it to finish.

Following `factorial(4)`:

```
going DOWN (each call waits for the next)

  ┌──────────────────────┐
  │ factorial(4)  4 * ?  │
  ├──────────────────────┤
  │ factorial(3)  3 * ?  │
  ├──────────────────────┤
  │ factorial(2)  2 * ?  │
  ├──────────────────────┤
  │ factorial(1)  return 1 │  ← base case, no more calls
  └──────────────────────┘

coming back UP (each paused call finishes)

  factorial(1) = 1
  factorial(2) = 2 * 1  = 2
  factorial(3) = 3 * 2  = 6
  factorial(4) = 4 * 6  = 24
```

That waiting stack is why recursion uses **O(depth) extra memory** — and why a deep recursion can overflow a stack that a simple loop would not.

**Backtracking** is the same stack idea plus an *undo*. Watch how combinations are built:

```
current = ""

  pick 1  → "1"
    pick 2  → "12"
      pick 3  → "123"  save ✓
      pop 3   → "12"   ← undo, so the next option is clean
      pick 4  → "124"  save ✓
      pop 4   → "12"
    pop 2   → "1"
    pick 3  → "13"
      ...
```

`current.append(...)` is the *push*; `current.deleteCharAt(...)` is the *pop*. Save → undo → try the next branch.

### The Hanoi pattern — trust the smaller call

Tower of Hanoi looks hard, but only because you try to see all the moves at once. The recursive view sees just **one disk and two smaller jobs**:

```
Move 3 disks  A → B  using C as helper

  1. move the top 2 disks  A → C   (using B)   ← same problem, one disk smaller
  2. move disk 3           A → B               ← the only move you "see"
  3. move those 2 disks    C → B   (using A)   ← same problem, one disk smaller
```

Unrolled for `n = 3`, each level is a paused frame on the stack:

```
hanoi(3, A → B, via C)
├── hanoi(2, A → C, via B)
│   ├── disk 1   A → B
│   ├── disk 2   A → C
│   └── disk 1   B → C
├── disk 3       A → B
└── hanoi(2, C → B, via A)
    ├── disk 1   C → A
    ├── disk 2   C → B
    └── disk 1   A → B
```

7 moves = `2^3 - 1`. Notice the function never touches a disk directly except in the `n == 1` base case — the rest of the work is just **swapping which rod plays which role** (`src`, `to`, `helper`). That role-swapping is the whole trick: the printed line stays `Move disk N from S to T`, only `S` and `T` change meaning.

---

## Recursion vs Loops

Same job, different tools:

```
   Loop                              Recursion
   ─────────────────────────         ─────────────────────────
   you write the repeat              the function repeats itself
   counts steps: for (i = 0 → n)     counts depth: call with n-1
   memory: O(1)                      memory: O(depth) call stack
   easy to read for simple walks     easy to read for trees, nesting,
                                     combinations, "divide and conquer"
```

**One-line rule:** if the problem naturally contains *smaller copies of itself*, recursion usually writes itself. If it is just "do this `n` times", use a loop.

---

## Cost at a Glance

`█` ≈ one unit of work — longer bar = slower

```
                            Recursive                  Loop / best fix
factorial(n)                █████  O(n)                █████  O(n)
fib(n) — naive              ██████████████  O(2^n)     ████  O(n) with DP
combinations (n, k)         ███  O(output × k)         n/a — needs k loops
hanoi(n)                    ██████████████  O(2^n)     no faster version —
                                                       2^n - 1 moves must be
                                                       printed anyway
extra memory                O(depth) call stack        O(1)
```

```
fib(5) — every repeated node is recomputed work

                    fib(5)
             ┌────────┴────────┐
          fib(4)              fib(3)      ← fib(3) computed twice
        ┌───┴───┐           ┌───┴───┐
     fib(3)  fib(2)      fib(2)  fib(1)  ← fib(2) computed 3 times
     ┌──┴──┐
  fib(2) fib(1)
```

Naive Fibonacci is the classic warning: recursion is elegant, not automatically fast. Memoization (caching answers) or a loop fixes it.

---

## Files

Every `.java` file is self-contained and runs on its own. Read them in this order — factorial first, because it is the cleanest "smaller copy of itself" story.

> **Note on `main`:** these files use Java's newer simplified entry point — `void main()` without `public static` (and no surrounding `class` in `CombinationGenerator.java`). Run them with a recent JDK via `java Factorial.java`. On older JDKs, wrap it in `public static void main(String[] args)`.

| # | File | Teaches | Cost |
|---|------|---------|------|
| 1 | [`Factorial.java`](Factorial.java) | The simplest recursion: one base case, one recursive case, the call stack unwinding | Time O(n), stack O(n) |
| 2 | [`Fibonacci.java`](Fibonacci.java) | **Two** recursive calls per invocation — a recursion *tree*, and why that explodes into O(2^n) | O(2^n) |
| 3 | [`CombinationGenerator.java`](CombinationGenerator.java) | Recursion as "loops inside loops", `start`/`i + 1` to avoid duplicates, and backtracking with `append` / `deleteCharAt` | O(output × k), stack O(k) |
| 4 | [`TowerOfHanoi.java`](TowerOfHanoi.java) | Two recursive calls around one printed move, **role-swapping** the rod arguments (`src`, `to`, `helper`), and a `static` counter that survives across every call | O(2^n) — exactly `2^n - 1` moves |

**Read them in that order** — file 1 gives you the base case + shrink pattern, file 2 shows what changes when a function calls itself twice, file 3 adds a loop and an undo on top of both, and file 4 makes the two-call structure visible with printed moves you can trace by hand.

> `CombinationGenerator.java` and `TowerOfHanoi.java` are longer on purpose: their header comments are the author's own reasoning ("why nested loops aren't enough", "we need just these 3 cases"), which is worth reading before the code.

## Output

Real console output of each file. The first three read a number (or string) from the keyboard, so the prompt line is shown too; Hanoi prints directly.

**`Factorial.java`** — input `5`
```
Enter a number for factorial: 5
The factorial of 5 is 120
```

**`Fibonacci.java`** — input `7`
```
Enter a number for Fibonacci: 7
The 7th fibonacci number is 13
```

**`CombinationGenerator.java`** — input `12345` and `2`
```
Enter your characters: 12345
Enter combination length (e.g., 2, 3, 4): 2
Generated pairs: 12 13 14 15 23 24 25 34 35 45
```

Same file, input `12345` and `3`
```
Enter your characters: 12345
Enter combination length (e.g., 2, 3, 4): 3
Generated pairs: 123 124 125 134 135 145 234 235 245 345
```

**`TowerOfHanoi.java`** — no input; it is hardwired to `n = 10` disks from `A` to `B` using `C`. That is `2^10 - 1 = 1023` lines, so only the start and end are shown:
```
Move disk 1 from A to C
Move disk 2 from A to B
Move disk 1 from C to B
Move disk 3 from A to C
...
Move disk 2 from A to B
Move disk 1 from C to B
Total number of moves: 1023
```
Try changing `10` to `3` in `main()` first — 7 lines you can follow by hand. Then `4` — 15 lines — and check that the disk numbers never repeat the same rod pattern by accident.

## Common Mistakes

```
❌ forgetting the base case        → StackOverflowError
❌ calling with the SAME input     → infinite recursion
❌ calling with a BIGGER input     → runs away from the base case
❌ forgetting to undo (backtrack)  → combinations come out mangled
❌ trusting recursion to be fast   → fib(n) needs memoization or a loop
```

Debug tip: print the argument at the **start** of every call. The output shows you the depth and tells you instantly whether the input is shrinking.

## Next Steps

Once these are comfortable: add **memoization** to `Fibonacci` (watch O(2^n) become O(n)), then try **sum of digits**, **power(x, n)**, **reverse a string**, **subsets / permutations**, and finally **binary tree traversals** — preorder/inorder/postorder are the same "two recursive calls around one visit" shape you already saw in `TowerOfHanoi`. Write each one from scratch — draw the call stack on paper before you write the code.
