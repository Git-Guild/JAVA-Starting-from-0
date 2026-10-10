# Java 25 in 4 Files

A guided tour of modern Java. Each file is a **single runnable script** - no
build tools, no boilerplate class declarations, no separate compile step.

## Completely new to Java? Start here

Three words you will see everywhere:

- **JDK** - the free Java toolbox you install once. It contains the `java`
  command used below. Download JDK 25 from
  [Adoptium](https://adoptium.net/).
- **terminal** - the text window where you type commands: PowerShell or
  Command Prompt on Windows, Terminal on Mac.
- **`.java` file** - a program written in Java. Every file in this folder is
  a complete program you can run on its own.

To run any of them: open a terminal **in this folder**, then type
`java` + space + the file name. Example:

```
java Basics.java
```

## Before you start

You need **JDK 25 or newer**. Check what you have:

```
java -version
```

You should see `25` (or higher). If you see something older, download the free
[LTS JDK 25 from Adoptium](https://adoptium.net/) and install it. See
[Troubleshooting](#troubleshooting) if the command is not found or the version
is wrong.

## How to run a file

Open a terminal in this folder, then type:

```
java Basics.java
```

That is the whole workflow. Java compiles the file in memory and runs it.
You never need `javac`, and no `.class` files are left behind.

Some files ask you to type something - just type a line and press Enter.
Press Enter on an empty prompt to accept the default.

> **Windows users:** double-click `RunAll.bat` to run all four demos in one go.

## Suggested order

| # | File | What you will learn |
|---|------|---------------------|
| 1 | `Java25Tour.java` | Quick taste of everything in one sitting |
| 2 | `Basics.java` | Printing, user input, lists, text across several lines, switches |
| 3 | `ModernTypes.java` | Records (tiny data classes), checking types neatly, extra collection tricks |
| 4 | `Concurrency.java` | Running many tasks at once with cheap virtual threads |

## Why there is no `class` in these files

Java 25 allows "compact source files". Instead of the old boilerplate:

```java
public class Basics {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

you can write just this:

```java
void main() {
    IO.println("Hello");
}
```

- No `public class Basics { ... }` wrapper - Java wraps the file in a hidden
  class for you, named after the file.
- No `public static void main(String[] args)` - a plain `void main()` works.
  Use `void main(String[] args)` if you need command-line arguments.
- `IO.println` / `IO.print` / `IO.readln` replace the old, longer ways to
  print and read text (`System.out.println` and `Scanner`). They are built
  in; nothing needs importing.

You can still write normal, classic Java - this is just less typing for
learning and scripting.

## Troubleshooting

### `error: cannot find symbol: class IO` or errors on `import module`
You are running an **older JDK**. Run `java -version` and install JDK 25
(see the link above). If you have several JDKs installed, your IDE or terminal
may be pointing at the old one.

### `Error: Could not find or load main class` / IDE says "no JDK configured"
Your `JAVA_HOME` environment variable is probably wrong or missing. It must
point at your JDK 25 folder, e.g. `C:\Program Files\Eclipse Adoptium\jdk-25.0.4.101-hotspot`.
On Windows: *Settings > About > Advanced system settings > Environment
Variables*, edit `JAVA_HOME`, restart the terminal/IDE.

### The program "hangs" at a prompt
It is waiting for input. Type a line and press Enter (or just press Enter to
use the default value).

### `IOException` or weird behaviour when piping input
`IO.readln` returns `null` when input ends (for example when piping from a
file). The demo files handle this with a default value.

### Characters show as `?` in the console
Some Windows consoles use a limited character set. Use **Windows Terminal**
(PowerShell) instead of the old `cmd` window. The examples in this folder use
plain ASCII, so they are safe either way.

### Structured concurrency does not compile
It is still a **preview** feature in Java 25 and needs a flag:

```
java --enable-preview --source 25 Concurrency.java
```

## Java 25 cheat-sheet (JEPs)

**Final in Java 25**
| JEP | Feature | Where |
|-----|---------|-------|
| 512 | Compact source files + instance main methods | all files |
| 511 | Module import declarations (`import module java.base;`) | `Java25Tour.java` |
| 513 | Flexible constructor bodies (statements before `super()`) | `ModernTypes.java` |
| 506 | Scoped values | `Concurrency.java` |
| 510 | Key Derivation Function API (`javax.crypto.KDF`) | `Java25Tour.java` |
| 519 | Compact object headers (`-XX:+UseCompactObjectHeaders`) | runtime flag |
| 521 | Generational Shenandoah GC | runtime flag |

**Preview / incubator in 25** (need `--enable-preview`, may change)
- JEP 505 Structured concurrency (5th preview)
- JEP 507 Primitive types in patterns / `instanceof` / `switch`
- JEP 502 Stable values
- JEP 470 PEM encodings of cryptographic objects
- JEP 508 Vector API (10th incubator)

**Worth knowing from Java 16-24** (all final, used in these files)
- `var` (10), records (16), sealed types (17), text blocks (15)
- Pattern matching: `instanceof` (16), `switch` + `when` guards (21)
- Virtual threads (21), sequenced collections (21)
- Unnamed variables `_` (22), markdown doc comments `///` (23)
- Stream Gatherers (24), multi-file source launch (22), Class-File API (24)

**Removed:** String templates (`STR."..."`) were previewed in 21-22, then
dropped. Use `.formatted(...)` or concatenation instead.
