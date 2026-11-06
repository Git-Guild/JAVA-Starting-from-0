# OOPM in Java

The object-oriented side of Java: classes, constructors, encapsulation,
polymorphism, hierarchies, and exceptions. Every folder has its own README with
the theory; the code files stay clean with only syntax and logic comments.
Where Java differs from C++, you'll get a short side note as you go.

## What to know first

1. [`How Java Works.md`](How%20Java%20Works.md) — **start here**: the JVM,
   bytecode vs machine code, stack/heap/garbage collection, references vs
   pointers, and the full C++ vs Java feature table.
2. The `Learning/` lessons (`L01`–`L09`) at the repo root — syntax, methods,
   arrays. `Learning/OOPS.java` is a good warm-up.

## What this folder has

| Folder | Phase | Files |
|--------|-------|-------|
| [`Phase 1/`](Phase%201/README.md) | Foundations | `ClassesandObjects` · `Constructor` · `Encapsulation` · `Polymorphism` |
| [`Phase 2/`](Phase%202/README.md) | Hierarchies | `Abstraction` · `Inheritance` · `StaticMembers` · `FinalKeyword` |
| [`Phase 3/`](Phase%203/README.md) | Java's own shape | `OperatorOverloading` (none!) · `FriendEquivalents` · `MoreAboutConstructors` · `EnumsAndRecords` |
| [`Exception Handling/`](Exception%20Handling/README.md) | Errors | `TryAndCatch` · `Error_Handling` · [`BuiltInExceptions.md`](Exception%20Handling/BuiltInExceptions.md) |

Run any file from this folder (Java 11+ compiles and runs in one step):

```
java "Phase 1/ClassesandObjects.java"
```

Every file is named exactly after the `public` class inside it: one public class
per file, file name = class name. That naming rule (and why names like
`1Classes and Objects.java` are illegal for a public class) is taught in
`Phase 1`.

## The big C++ → Java differences you'll meet here

| C++ | Java | Where |
|---|---|---|
| `struct` | `class` / `record` / `enum` | Phase 1 + Phase 3 |
| `virtual` keyword | instance methods virtual by default | Phase 1 |
| multiple class inheritance | one `extends`, many `implements` | Phase 2 |
| `friend` | package access + nested classes | Phase 3 |
| operator overloading | methods only (`add`, `equals`, `compareTo`) | Phase 3 |
| destructor / `delete` | garbage collector | How Java Works §4 |
| pointers (`*`, `&`, `->`) | references only | How Java Works §3 |
| `const` | `final` | Phase 2 |
| all exceptions unchecked | **checked** exceptions + `finally` | Exception Handling |

## What to do after this folder

- Practice on your own: build a grade book, an electricity-bill calculator, or
  a word sorter — classic OOP drills, now with proper exception handling.
- [`../Basic DSA/`](../Basic%20DSA/README.md) — put classes and references to
  work on real data structures.
- `Learning/Javac 25/` — the modern style (records, pattern matching) you first
  met in Phase 3.
