# Phase 3: Java's Own Shape

Where Java deliberately differs from C++: no operator overloading, no `friend`,
richer constructor tools — plus `enum` and `record`, the answers to C++'s
`struct`.

## What to know first

- Finish [`../Phase 2/`](../Phase%202/README.md) — this phase assumes
  constructors, access levels, interfaces, and overriding are familiar.
- Skim [`../How Java Works.md`](../How%20Java%20Works.md) §6 (the C++ vs Java
  feature table) — `OperatorOverloading` and `FriendEquivalents` expand rows of
  that table.

## What this folder has

| # | File | What it teaches |
|---|------|-----------------|
| 9 | [`OperatorOverloading.java`](OperatorOverloading.java) | Java has **none** — the `+`-on-String exception, and the method-based replacements (`add`, `equals`, `compareTo`, `toString`) |
| 10 | [`FriendEquivalents.java`](FriendEquivalents.java) | no `friend` keyword — package access, nested classes, getters |
| 11 | [`MoreAboutConstructors.java`](MoreAboutConstructors.java) | `this()`/`super()` chaining, copy constructors, **aliasing**, initialization order, private constructors + factories, why there are no move constructors |
| 12 | [`EnumsAndRecords.java`](EnumsAndRecords.java) | **Java's answers to `struct`**: enums + records (and why records beat structs) |

## Theory

### Operator overloading: the short answer is no
Java operators keep their built-in meaning forever. The single exception is `+`
on `String` (concatenation), which the JVM bakes in. You can never overload an
operator yourself — Gosling's choice, to keep the language small and avoid C++'s
corner cases. Instead Java programmers use named methods:

| C++ | Java |
|---|---|
| `car1 + car2` | `car1.add(car2)` |
| `car1 == car2` | `car1.equals(car2)` (`==` compares references) |
| `car1 < car2` | `car1.compareTo(car2)` via `Comparable<T>` |
| `cout << car` | override `toString()`, then `println(car)` |
| `list += car` | `list.add(car)` |

Method **overloading** still exists (that's compile-time polymorphism from
Phase 1) — it just isn't operator overloading.

### friend, replaced by three tools
C++ `friend` granted outside code access to privates. Java refuses the
hand-written trust grant and offers:

1. **Package-private (default) access** — everyone in the same package sees
   these members. The closest match to "friend circle".
2. **Nested classes** — a class inside a class reads the outer class's
   `private` members automatically. In C++ this needed `friend class Inner;`.
3. **Getters with validation** — the idiomatic answer: don't hand out private
   access, hand out controlled methods.

(Reflection *can* break private at runtime — frameworks like Spring use it —
but it's a hack, not a design tool.)

### Constructors: what Java added and removed
- **Chaining:** `this(...)` delegates to another constructor of the same class,
  `super(...)` to the parent. Both must be the first statement.
- **Copy constructors:** Java's `=` copies only the **reference** — two names
  can point at one object (aliasing). There is no automatic deep copy like
  C++, so if you want a copy, you write `Account(Account other)`.
- **No move constructors:** C++ moved objects to dodge expensive copies; Java
  copies references in O(1) and has no ownership/`delete` to transfer, so
  moving would gain nothing.
- **Initialization order:** static fields/blocks (once, on class load) →
  instance field initialisers (top to bottom) → constructor body.
- **Private constructor + static factory** (`DatabaseConnection.connect(...)`):
  the class alone decides how instances are made — cache them, return
  meaningful `null`s, hand out subclass instances. `new` can't do any of that.

### enum — a full class for fixed constants
`enum Season { WINTER, SPRING, SUMMER, FALL }` — those are the *only* Season
objects that will ever exist. Compared to C++ enums:

- **Type-safe:** `Season s = 2;` is a compile error; C++ silently allowed it.
- Each constant is an object: enums can have `private final` fields, a
  constructor that runs once per constant, methods, and `implements` interfaces.
- Free tools: `values()`, `valueOf()`, `ordinal()`, `name()`, and arrow-form
  `switch` with no fall-through.
- Enums are implicitly `final` and extend `java.lang.Enum` — never subclassed.

### record — the struct Java was missing (Java 16+)
`record Point(int x, int y)` gives you in one line:

- `private final` fields `x`, `y` (encapsulated **and** immutable)
- the all-arguments constructor `Point(3, 4)`
- getters named after the field: `p.x()` (not `getX()`)
- correct `equals()`, `hashCode()`, `toString()` — value comparison out of the box

Rules: records are implicitly `final` (no extending, they extend
`java.lang.Record`), they can't add mutable instance fields, but they *can* have
methods and a **compact constructor** for validation:

```java
record Point(int x, int y) {
    Point {
        if (x < 0 || y < 0) throw new IllegalArgumentException("negative");
    }
}
```

**Choosing:** fixed set of names → `enum`; immutable data carrier → `record`;
anything with mutable state, behaviour, or inheritance → `class`.

## What to do next

- Continue to [`../Exception Handling/`](../Exception%20Handling/README.md):
  the full `Throwable` hierarchy, checked vs unchecked, `finally`, and custom
  exceptions — the biggest Java-only difference left.
- Experiment: implement `Comparable` on your own class and sort an array of it;
  make a record field non-final and read the compile error.
