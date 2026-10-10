# Learning - Java from Zero

This folder is the main course: short lessons that start at "Hello World"
and build up to object-oriented programming and threads. Each file is a
complete program you can run on its own.

Newer Java 25 features live in the
[`Javac 25`](Javac%2025/README.md) subfolder - do that one after the
lessons here.

## Before you start

You need a **JDK** (the free Java toolbox that contains the `java`
command). Check what you have:

```
java -version
```

Any recent version works for the lessons here (JDK 25 is recommended, since
the subfolder needs it). If the command is not found, download JDK 25 from
[Adoptium](https://adoptium.net/).

## How to run a file

Open a terminal **in this folder**, then type `java` + space + the file
name. Example:

```
java L01.java
```

That single line compiles and runs the file. You do not need `javac`.

Some lessons ask you to type something (they use `Scanner`) - type your
answer and press Enter. If nothing seems to happen, the program is waiting
for input.

## Suggested order

### Lessons

| # | File | What you will learn |
|---|------|---------------------|
| 1 | `L01.java` | Hello World, basic syntax, the `main` method |
| 2 | `L02_UserInput.java` | Reading what the user types (Scanner) |
| 3 | `L03_Operators.java` | Math operators: `+ - * / %` and friends |
| 4 | `L04_String.java` | Working with text (string methods) |
| 5 | `L05_Conditional.java` | Making decisions: `if`, `else`, `switch` |
| 6 | `L06_Printf.java` | Printing neatly formatted output |
| 7 | `L07_LOOPS.java` | Repeating code: `for`, `while` |
| 8 | `L08_Arrays.java` | Lists of values in one variable |
| 9 | `L09_Methods.java` | Bundling code into reusable methods |

### Extra examples

After the lessons, these show one topic each in more depth:

| File | Topic |
|------|-------|
| `ArrayListExample.java` | ArrayList - a list that can grow and shrink |
| `HashMapsexample.java` | HashMap - looking values up by name (key) |
| `MiscEnumsInterface.java` | Enums (fixed lists) and interfaces (promises) |
| `OOPS.java` | Object-oriented programming: the four pillars |
| `OOPS2.java` | Aggregation - classes built from other classes |
| `FriendsOOPS.java` | A small class with a `static` counter |
| `secondOOPS.java` | The smallest possible class example |
| `Threading.java` | Threads - running several tasks at once |

### Then

| Folder | What it is |
|--------|------------|
| [`Javac 25/`](Javac%2025/README.md) | Modern Java 25: no class boilerplate, records, virtual threads, and more |

## Old style vs Java 25 style

The files in **this** folder use the classic Java style:

```java
public class L01 {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
```

The files in **`Javac 25/`** use the new Java 25 style with almost no
boilerplate:

```java
void main() {
    IO.println("Hello");
}
```

Both are real Java. Learn the classic style here first - it is what most
existing code and tutorials use.

## What the comment tags mean

The comments use tags from the free **Colorful Comments** VS Code
extension (search for it in the Extensions view):

| Tag | Meaning |
|-----|---------|
| `//TODO` | what the file is about |
| `//*` | section start / the big idea |
| `//!` | warning or "read this" |
| `//&`, `//~` | small side notes |

Without the extension the tags are just ordinary text - everything still
works.

## Common problems

### `Error: Could not find or load main class L01`
The class name inside the file must match the file name. `L01.java` must
contain `public class L01`. If you rename a file, rename the class too.

### The program stops and nothing happens
It is waiting for input. Type a line and press Enter.

### `.class` files appearing in the folder
If you ever compile with `javac L01.java`, Java saves a `.class` file next
to it. You can delete these safely; they are already ignored by git. Running
with `java L01.java` does not create them.
