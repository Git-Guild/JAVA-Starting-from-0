# Phase 1: The Four Foundations

Classes and objects, constructors, encapsulation, polymorphism — the base every
later phase builds on.

## What to know first

- Read [`../How Java Works.md`](../How%20Java%20Works.md) — JVM, bytecode,
  references vs pointers, garbage collection. Everything here assumes it.
- Finish the `Learning/` lessons at the repo root (`L01`–`L09`): syntax,
  methods, arrays, and basic `if`/loops.
- Helpful but optional: `Learning/OOPS.java` for a first taste of OOP.

## What this folder has

| # | File | What it teaches |
|---|------|-----------------|
| 1 | [`ClassesandObjects.java`](ClassesandObjects.java) | class vs object, method overloading, the `main` signature, **public class vs normal class rules** |
| 2 | [`Constructor.java`](Constructor.java) | constructors, `this.` vs `this()`, the default-constructor rule, why there is no destructor |
| 3 | [`Encapsulation.java`](Encapsulation.java) | `private` fields, getters/setters, the 4 access levels |
| 4 | [`Polymorphism.java`](Polymorphism.java) | overloading vs overriding, virtual-by-default, `@Override`, upcasting |

Run any file with `java "Phase 1/ClassesandObjects.java"` (or `javac` it and run
the class name — both work, every file is named after its public class).

## Theory

### Class and object
A **class** is a blueprint; an **object** is one thing built from that blueprint.
"A class is a blueprint of an object and an object is an instance of a class."
Every class you write secretly extends `java.lang.Object`, so every object gets
`toString()`, `equals()`, `hashCode()` for free.

### public class vs normal class (exam favourite)
A top-level class has exactly two legal access levels:

- `public class Foo` — visible everywhere. The file **must** be named `Foo.java`,
  and there can be **at most one** such class per file.
- `class Foo` (no keyword) — **package-private / default**: visible only inside
  its own package (its folder of classes).
- `private` or `protected` on a top-level class is a **compile error** — those
  only work on members inside a class, or on nested classes.

**File-naming convention (why this can't work in Java):** the file must be
named after its public class, and a class name must be a valid Java identifier —
letters, digits, `_` and `$` only, **no spaces, and never starting with a
digit**. So a C++ file named `1Classes and Objects.cpp` is perfectly fine, but
the same name in Java could never hold a `public` class. (An ordinary file name has no
such limits — but then the class inside is forced to be non-public, see below.)

**What happens if you create a class outside the public class?** Perfectly legal:
a file may hold any number of extra non-public classes. Each becomes its own
`.class` file (`Helper.class`) and stays hidden from other packages. What is
*not* legal is a second `public` class — javac says
`class Helper is public, should be declared in a file named Helper.java`.

Every file in this folder follows that convention: `ClassesandObjects.java`
holds `public class ClassesandObjects` plus the extra non-public `Employee` and
`Helper` — one public class named like the file, everything else non-public.
Open `ClassesandObjects.java` to see all three at once.

### Packages, projects, and package-private (read this before `ClassesandObjects.java`)

**Project** = the folder you open in VS Code. The whole `JAVA-Starting-from-0`
repo is one project. Nothing more magical than that — no configuration, no
setup.

**Package** = a folder of `.java` files. A file *may* begin with a package line
naming the folder it lives in:

```java
package phase1;    // means: this file belongs to the package stored in folder "phase1"
```

The folder path and the package name must match. A file with **no** `package`
line (like every file in this OOPM folder) belongs to the **default package** —
one big shared package where all such files can see each other.

**package-private** = the no-keyword default, and it does **not** mean private:

```java
class Employee {
    String Name;      // no keyword  = package-private: the WHOLE package can touch it
    private int Age;   // private     = only Employee itself can touch it
}
```

- `private int Age` → only code *inside* `Employee` may write `e.Age`.
- `String Name` (package-private) → **any class in the same package** may write
  `e.Name` directly. "Private to the package" — not private to the class, not
  private to the file.

**The wtf moment, explained.** In `ClassesandObjects.java`, `main` does
`emp1.Name = "Yuvraj"` with no setter — and it compiles. That surprises you
because `Name` *looks* hidden. It isn't: `ClassesandObjects` and `Employee` are
both in the same package (they even live in the same file, with no `package`
line → default package).
Move `main` into a different package and the very same line dies with:

```
error: Name is not public in Employee; cannot be accessed from outside package
```

That is the whole difference:

| | `private` | *(no keyword)* package-private |
|---|---|---|
| the declaring class | yes | yes |
| other classes in the **same** package | **no** | **yes** |
| classes in other packages | no | no |

Why Java keeps this middle level: cooperating classes in one folder can share
internals while staying invisible to the rest of the world — the Java
replacement for C++ `friend` (see `Phase 3/FriendEquivalents.java`).
Real projects put a `package` line on every file; the default package is for
small learning folders like this one (code in the default package can't be
imported from other packages, which is why big projects never use it).

### struct in Java
There is no `struct` keyword. For a plain data bundle use:

1. **`class`** — the normal blueprint (members are package-private by default;
   C++ `class` defaulted to private, C++ `struct` to public — Java has no
   struct so it picked the middle ground).
2. **`record`** — Java 16+, the real struct replacement (auto constructor,
   getters, `equals`, `toString`).
3. **`enum`** — a full class for a fixed set of constants.

Both are covered in detail in `Phase 3/EnumsAndRecords.java`.

### Constructors
Rules (same as C++): same name as the class, no return type, overloadable.
Once you write **any** constructor, javac stops inserting the default no-arg one.
Java differences:

- `this.field` disambiguates (C++ used `this->field`), `this()` delegates to
  another constructor of the same class.
- There is **no destructor**. `new` always allocates on the heap, and the
  Garbage Collector reclaims the memory — silently, at a time you can't predict.

### Encapsulation
Keep fields `private`, expose `getters`/`setters`, and put validation inside the
setter (`Encapsulation.java` rejects ages under 18). The four access levels:

| Keyword | Visible in |
|---|---|
| `private` | the same class only |
| *(none — default)* | the same package |
| `protected` | the same package + subclasses |
| `public` | everywhere |

### Polymorphism
- **Overloading** — same method name, different parameters. The **compiler**
  picks the version, based on the reference type you wrote.
- **Overriding** — the derived class redefines the base method. The **JVM**
  picks the version at run time, based on the real object.

Java difference: instance methods are **virtual by default** — no `virtual`
keyword, no `override` keyword. Opt out with `final`, `static` or `private`.
`@Override` is just a compiler check (a typo catcher), it changes nothing.
Upcasting (`Employee e = dev;`) is implicit and always safe — Java needs no
pointers for the base-reference trick that C++ did with `Employee* e = &d;`.

## What to do next

- Continue to [`../Phase 2/`](../Phase%202/README.md): abstraction,
  inheritance, `static`, `final`.
- Experiment: make `Name` private in `ClassesandObjects.java` and watch the
  compile errors move to `main`; delete a constructor in `Constructor.java` and
  try `new Employee(...)`.
