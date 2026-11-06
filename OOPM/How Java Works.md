# How Java Actually Works (and how it differs from C++)

Read this **before** the phases. Everything in the Java files makes more sense once you know
what the machine is actually doing behind your code.

## 1. The Journey of a Program

### C++
```
MyProgram.cpp  ──(compiler: g++/clang)──►  MyProgram.exe   (machine code for ONE cpu/os)
```
The compiler translates your code **directly** into CPU instructions.
The output runs only on that OS/CPU — recompile for every platform.

### Java
```
MyProgram.java  ──(javac)──►  MyProgram.class  ──(java)──►  JVM  ──(JIT)──►  machine code
                              (BYTECODE)        (runs it)
```
1. `javac MyProgram.java` → produces `MyProgram.class`
2. `MyProgram.class` does **NOT** contain x86/ARM machine code.
   It contains **bytecode** — instructions for a *virtual* CPU called the **JVM**
   (Java Virtual Machine).
3. `java MyProgram` starts the JVM, which loads your `.class` and executes it:
   * an **interpreter** runs bytecode instruction by instruction
   * a **JIT compiler** (Just-In-Time) detects "hot" code running again and again,
     compiles *that part* to real machine code for speed
4. Before running, the JVM's **Bytecode Verifier** checks the code for unsafe
   operations (this is part of why Java is memory-safe by default).

**"Write Once, Run Anywhere"**: you ship the `.class`/`.jar`, and *any* OS that has a
JVM installed can run it. The OS-specific work is done by the JVM, not by you.

> C++ has no such layer — that's the single biggest architectural difference.

## 2. JVM Memory Model

| Region | What lives there | Lifetime | C++ equivalent |
|---|---|---|---|
| **Stack** | one stack *per thread*; method call frames; primitive values (`int`, `boolean`...) and **references** (the variable that *points to* an object) | auto-erased the moment the method returns | same idea as C++ stack |
| **Heap** | **every object and array you create with `new`** | lives until the **Garbage Collector** decides it's unreachable | C++ heap, but you never `delete` |
| **Metaspace** (class data area) | loaded `.class` definitions, `static` variables, string constants | as long as the class is loaded | no real C++ equivalent (closest: global/static storage) |

Key rules:
* **`new` always allocates on the heap** — in C++ you chose stack vs heap; in Java you don't.
* Local variable declarations (`int x = 5;`) go on the stack. The *object* a reference
  points to goes on the heap.
* Stack overflow → `StackOverflowError`. Heap exhausted → `OutOfMemoryError`.

## 3. References, Not Pointers

```java
Employee e = new Employee();   // e is a REFERENCE (like a remote control), not the object
```

* No `*`, no `&`, no `->`, no pointer arithmetic, no getting the "address of" anything.
* You can pass `null` around — dereferencing it gives you a `NullPointerException`
  (C++ would just segfault with no useful message).
* **Assignment copies the reference, not the object:**
  ```java
  Employee a = new Employee("Raj");
  Employee b = a;      // b and a are two remotes for the SAME object (aliasing!)
  b.Name = "Hacked";   // also changes a.Name
  ```
  In C++ `Employee b = a;` would (usually) copy the object. In Java it never does —
  there is **no automatic deep copy** (see `Phase 3\MoreAboutConstructors.java`).
* For objects, `==` compares **references** (same remote or not), so use `.equals()`
  to compare actual content.

## 4. Garbage Collection Instead of Destructors

* You write **no destructor** — `~Employee()` does not exist in Java.
* You write **no `delete`** — you simply stop using the object.
* The **Garbage Collector** finds objects nothing points to anymore and frees them.
* Consequences:
  * **GC is not deterministic.** You cannot know exactly when (or whether) it runs.
    `System.gc()` is only a *hint*.
  * There is **no RAII** — you cannot rely on "end of scope" to release files/sockets.
    Use `finally`, **try-with-resources**, or `Cleanable` instead.
  * `finalize()` existed once as a destructor-ish hook — **deprecated since Java 9,
    never use it.**
* C++ destructors run at a *precise* moment (scope exit, reverse order). Java gives you
  **no such guarantee** — this is a deliberate trade: safety and simplicity over control.

## 5. Everything Sits Inside a Class

* **No global functions.** C++ `int add(int a, int b){...}` becomes, in Java, a method
  inside a class (usually `static` if it doesn't touch object data).
* **No global variables.** The closest thing is a `static` field (see
  `Phase 2\StaticMembers.java`).
* **No `#include`, no header files.** Classes are found through **packages** and the
  **classpath**; `import` only *names* things, it does not paste code like `#include`.
* **File / class naming rules (important, exam-relevant):**
  * A **top-level** class can be `public` or **package-private** (no keyword).
    It can *never* be `private` or `protected` — those only work on members/nested classes.
  * **At most one** `public` top-level class per file, and the file **must** be named
    `ThatClass.java`.
  * A class name is a **Java identifier**: letters, digits, `_`, `$` — **no spaces,
    no starting digit**. So a C++-style name like `1Classes and Objects.java` could
    never hold a `public` class in Java.
  * If the class is **not** `public`, the file name can be *anything* — that's the
    escape hatch for holding several classes in one file.
  * You **can** put extra classes in the same file — they just can't be `public`
    (for example `ClassesandObjects.java` also holds `Employee` and `Helper`).
  * Every file in this folder follows the convention **file name = public class
    name**, so `java File.java` runs it directly. Full details in
    `Phase 1\README.md`.

## 6. C++ vs Java — OOP Feature-by-Feature

| Feature | C++ | Java |
|---|---|---|
| `struct` | yes (public-by-default data bundle) | **does not exist** → use `class`, or better: **`record`** (data) / **`enum`** (constants) |
| default member access | `class` = private, `struct` = public | members default to **package-private**; classes *usually* make fields `private` |
| `virtual` / dynamic dispatch | opt-in with `virtual` | **on by default** for instance methods (`final`/`static`/`private` opt out) |
| multiple inheritance of classes | yes (causes the diamond problem) | **no** — single `extends`, but *many* `implements` (interfaces) |
| `friend` | yes | **no keyword** → package access + nested classes (Java makes nested classes friends for free) |
| operator overloading | yes | **only `+` on String** — everything else must be a method |
| destructor / `delete` | deterministic, manual | **Garbage Collector**, non-deterministic, automatic |
| pointers | raw (`*`, `&`, `->`) | **references only** — no address-of, no arithmetic |
| memory bugs | segfaults, double-free, leaks | mostly `NullPointerException`, `OutOfMemoryError` |
| `const` | rich `const` system | `final` only (variable / method / class) — simpler, weaker |
| templates | yes | **generics** (type erasure — checked at compile time, erased at runtime) |
| error handling | `throw`/`catch`, everything unchecked | `throw`/`catch` **+ `finally`**, plus **checked exceptions** enforced by the compiler |
| compilation | native machine code | **bytecode** → JVM → JIT |

## 7. Running the Files in This Folder

```powershell
# from the OOPM folder — folder names contain spaces, so quotes are required
javac "Phase 1\ClassesandObjects.java"
java ClassesandObjects

# one-file convenience: java can compile AND run in one step (Java 11+)
java "Phase 1\ClassesandObjects.java"
```

Every file is named exactly after its `public` class (one public class per file,
file name = class name), which is why both commands work. See section 5 above.

*Next: `Phase 1\ClassesandObjects.java`*
