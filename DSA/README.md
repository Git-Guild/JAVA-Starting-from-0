# Basic DSA

This folder is a hands-on introduction to **data structures** in Java. Each data structure gets its own subfolder, and inside it each operation gets its own small, self-contained `.java` file you can read and run on its own.

Expect plain, beginner-friendly code with comments that explain the *why*, not just the *what*. It will not be as expansive as a full algorithms course — the goal is to make the **syntax and core logic** click. After that, the best teacher is solving problems and coding challenges.

## What's Inside

| Data Structure | Folder | What you'll learn |
|----------------|--------|-------------------|
| [Linked List](Linked%20List/README.md) | `Linked List/` | Nodes, head references, reference manipulation, two ways to build a list (manual linking and a `LinkedList` class), insert at first / last / position, delete at front, traversal, heap memory (`new`, and why Java needs no `delete`) |
| ↳ operations | `Linked List/Insertion/`, `Linked List/Deletion/` | One `.java` per operation — insert at first, insert at last, insert at position, delete at the beginning, delete at the end, delete at a position |

## Prerequisites

You should already be comfortable with (from the `Learning/` folder at the repo root):

| Concept | File |
|---------|------|
| Methods, passing values into methods | [`Learning/L09_Methods.java`](../Learning/L09_Methods.java) |
| Classes, objects, constructors | [`Learning/OOPS.java`](../Learning/OOPS.java) |
| ArrayList — the list that already exists (compare it with a hand-built one here) | [`Learning/ArrayListExample.java`](../Learning/ArrayListExample.java) |

**Note on `struct`:** `struct` is a C++ thing, **not Java**. C++ has both `struct` and `class`; Java only has `class`. Everywhere the C++ version of this folder uses `struct Node`, the Java version simply uses `class Node` — for a plain data holder like a node, a Java class does the exact same job.

Language details that appear in these files (`null`, constructors, `new`) are explained **in place, once**, in the first file that uses them — later files don't repeat them. They're tools for building data structures, not separate topics in this folder.

## How to Run

Because folder names contain spaces, quote the path. Example on Windows:

```bash
java "Linked List/Insertion/InsertAtFirst.java"
```

On Linux/macOS:

```bash
java "Linked List/Insertion/InsertAtFirst.java"
```

That single line compiles and runs the file — you do not need `javac` (same as in the [Learning folder](../Learning/README.md)). Or open any file in VS Code and press `Ctrl+F5`.

## Conventions in This Folder

- **One operation per file.** Each `.java` demonstrates exactly one idea (e.g., insert at last, delete at the beginning).
- **Self-contained files.** Every file redefines its own `Node` plus its helpers (`printList`, or the `LinkedList` class) so it runs alone. Repeated code has no re-explanations — concepts are taught only in the first file that needs them, then the later files say "see `InsertAtFirst.java`".
- **Classic Java.** `null` instead of C++'s `NULL`/`nullptr`, `class` only (no `struct` — that's C++), garbage collection instead of `delete`, and returning the updated head from a method instead of C++'s pass-by-reference (`Node *&head`).
- **Actual output included.** The [Linked List README](Linked%20List/README.md) shows the real console output of every file, and the files that document it inline keep it in a comment block at the end.
- **Heap memory is the whole point — but Java cleans up for you.** Linked lists live on the heap via `new`. In C++ you must `delete` every node yourself or leak memory; in Java the garbage collector frees any object once nothing points to it, so the basics file explains the C++ cleanup loop and why Java skips it. The later operation-focused files stop right after printing so each file stays on one idea.

More data structures will be added here as separate subfolders over time — each with its own README explaining the concept.
