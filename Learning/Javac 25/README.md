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

## Suggested order

| # | File | What is inside |
|---|------|----------------|
| 1 | `Basics.java` | Printing, reading input, lists and maps, loops, `_`, text blocks, switches, string helpers, streams |
| 2 | `ModernTypes.java` | Records, enums, sealed types, pattern matching, code before `super()`, both ends of lists, stream gatherers |
| 3 | `Concurrency.java` | Virtual threads, running 1000 jobs at once, scoped values, structured concurrency (preview) |
| 4 | `Cryptography_Key_Security.java` | A small ticket desk that combines everything, plus a deep dive on the new KDF API |

Each file prints what to run next, so just follow the messages.

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

## What the comment tags mean

The comments in these files use tags from the free **Colorful Comments**
VS Code extension (search for "Colorful Comments" in the Extensions view to
install it):

| Tag | Meaning |
|-----|---------|
| `//*` | section start / the big idea |
| `//!` | warning or "read this" |
| `//&` | a small side note |

Without the extension the tags are just ordinary text - everything still
compiles and runs the same.

## What else is new in Java 25?

These work in the background, so there is no code to show in these files:

- **Smaller objects** - start Java with the option
  `-XX:+UseCompactObjectHeaders` and your program uses less memory. Every
  object carries a small "label"; this option makes the label smaller.
- **A better memory cleaner** - Java throws away data you no longer need. A
  newer cleaner called Generational Shenandoah is now fully supported, and it
  works with fewer pauses.
- **Faster start and better measuring tools** - for programs that run on big
  servers.

**Still unfinished in Java 25** (you must add `--enable-preview` to try them):

- Structured concurrency (see the end of `Concurrency.java`)
- Primitive types in patterns (use plain `int`, `double` in a pattern)
- Stable values (a value that is filled in once, only when first needed)

**Gone:** String templates (`STR."..."`) were tried in Java 21-22 and then
removed. Use `.formatted(...)` instead, as these files do.

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

### The editor keeps adding `import ...` lines when I save
That is VS Code organizing imports on save. In these compact files imports
are not needed (Java already brings in the standard tools). Turn it off with
`"java.saveActions.organizeImports": false` in your VS Code settings, then
delete the import lines.

## Java 25 cheat-sheet (JEPs)

**Final in Java 25**
| JEP | Feature | Where |
|-----|---------|-------|
| 512 | Compact source files + instance main methods | all files |
| 511 | Module import declarations (`import module java.base;`) | optional in all files |
| 513 | Flexible constructor bodies (statements before `super()`) | `ModernTypes.java` |
| 506 | Scoped values | `Concurrency.java`, `Cryptography_Key_Security.java` |
| 510 | Key Derivation Function API (`javax.crypto.KDF`) | `Cryptography_Key_Security.java` |
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
