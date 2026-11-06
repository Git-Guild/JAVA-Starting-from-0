# Phase 2: Building Class Hierarchies

Abstraction, inheritance, `static` members, and `final` — how classes relate to
each other and how class-wide state and constants work.

## What to know first

- Finish [`../Phase 1/`](../Phase%201/README.md) — this phase reuses
  constructors, `private`/`protected`, and overriding constantly.
- [`../How Java Works.md`](../How%20Java%20Works.md) §2–4: stack vs heap,
  references, and why there is no destructor.

## What this folder has

| # | File | What it teaches |
|---|------|-----------------|
| 5 | [`Abstraction.java`](Abstraction.java) | `abstract class` vs `interface`, `default` methods, `implements`, why neither can be instantiated |
| 6 | [`Inheritance.java`](Inheritance.java) | `extends`, single/hierarchical/multilevel inheritance, **multiple inheritance via interfaces**, `super()` |
| 7 | [`StaticMembers.java`](StaticMembers.java) | **what `static` actually is**, static fields/methods/blocks, why `main` must be static |
| 8 | [`FinalKeyword.java`](FinalKeyword.java) | **`final` explained**, constants, immutability, `final` vs `finally` vs `finalize` |

## Theory

### Abstraction
Show **what** a thing does, hide **how**. Java has two tools:

| | `abstract class` | `interface` |
|---|---|---|
| Fields | yes (real state) | only `public static final` constants |
| Constructor | yes | no |
| Methods | abstract **and** normal methods | `abstract` by default; `default`/`static` methods since Java 8 |
| Extends | one class | implemented by many classes, extended by many interfaces |

A class uses `extends` for a parent class and `implements` for interfaces.
A `default` method has a body inside an interface — it's how Java adds methods
to an old interface without breaking every class that already implements it.
Neither an interface nor an abstract class can be instantiated: contract only.

### Inheritance
Syntax: `class Child extends Parent` — always public inheritance; Java has no
`: private`/`: protected` inheritance. Key rules:

- **One class parent only.** Java refuses C++'s multiple inheritance (and its
  diamond problem). Extra behaviour comes from `implements` instead —
  `class TechLead extends Employee implements Speaker`.
- `super(...)` calls the parent constructor and must be the first statement.
  If you write no `super()`/`this()`, javac inserts `super()` for you.
- `private` parent members are not directly visible; children use the getters.
- Every class ends up under `java.lang.Object`.
- `final class` = cannot be extended (Java's `String`).

Types shown in `Inheritance.java`: single (Dev → Employee), hierarchical (Teacher and Dev
are siblings), multilevel (LeadDev → Dev → Employee), multiple/hybrid via
interfaces.

### static — the point of it
`static` means **belongs to the class, not to an object**: one shared copy for
the whole program, initialised when the class loads. Java has **no global
variables and no global functions** — `static` is the legal substitute. That is
its entire purpose:

- shared counter/config across all objects → `static` field
- utility that uses only its parameters → `static` method (`Math.max`)
- program-wide constant → `static final` (next file)
- one-time setup → `static { }` block

Rules:

- a static method can only touch static members (there is no `this`)
- call statics via the class name (`Employee.PrintEmployeeCount()`)
- `main()` is static because the JVM invokes it before any object exists —
  which is also why `String[] args` must be static
- C++ needed `int Employee::employeeCount = 0;` outside the class; Java doesn't
  — declaration plus initialiser (or a static block) is enough

### final
Three meanings depending on what it precedes:

- `final` **variable** — constant, assigned once (C++'s `const`).
- `final` **method** — cannot be overridden (can still be overloaded).
- `final` **class** — cannot be extended (`String`, `Integer`).
- `static final` — the program-wide constant, `ALL_CAPS` by convention.

**The trap:** `final Employee e = ...` freezes the *reference*, not the object —
`e.Name = "x"` still works. To freeze the data too you need an immutable class:
`private final` fields, no setters, often a `final` class.

**final vs finally vs finalize:** `final` is the keyword above; `finally` is a
cleanup block after `try`/`catch` (Exception phase); `finalize()` is the GC hook
that is deprecated since Java 9 — never use it.

## What to do next

- Continue to [`../Phase 3/`](../Phase%203/README.md): what Java *removed*
  from C++ (operator overloading, `friend`) and what it *added* (enums,
  records, constructor chaining, factories).
- Experiment: give an interface a second `default` method and watch every
  implementer inherit it; try to `extends` a `final class` and read the error.
