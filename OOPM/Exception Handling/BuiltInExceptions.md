# Built-in Java Exceptions

The JDK ships hundreds of exception classes. This file lists the ones you
actually meet in real programs, grouped the way `Throwable` groups them. The
*rules* (checked vs unchecked, `catch`/`throw`/`finally`) live in
[`README.md`](README.md); this page is the catalog you look things up in.

How to read it:

- **Unchecked** = `RuntimeException` subclasses — the compiler never forces you
  to catch them; treat them as bugs to fix, not to catch.
- **Checked** = everything else under `Exception` — you must `catch` them or
  declare `throws` on the method.
- **`Error`** = JVM/system failures — never catch them.

## The tree at a glance

```text
Throwable
├── Error                     never catch — the JVM itself is failing
│   ├── VirtualMachineError   OutOfMemoryError, StackOverflowError, InternalError
│   ├── AssertionError        a failed `assert` statement
│   └── LinkageError          class failed to load when the program ran
└── Exception
    ├── RuntimeException       UNCHECKED — fix the bug, don't catch
    │   ├── NullPointerException
    │   ├── IllegalArgumentException → NumberFormatException, PatternSyntaxException
    │   ├── IllegalStateException → UnsupportedOperationException
    │   ├── IndexOutOfBoundsException → Array…, String…
    │   └── ConcurrentModificationException, NoSuchElementException, …
    └── everything else        CHECKED — catch it or declare `throws`
        ├── IOException → FileNotFoundException, EOFException, MalformedURLException
        ├── SQLException
        ├── InterruptedException
        ├── ClassNotFoundException
        └── ParseException, URISyntaxException, TimeoutException, …
```

(`→` marks notable subclasses. Your own exceptions slot into the same tree:
`extends Exception` = checked, `extends RuntimeException` = unchecked.)

## Errors — never catch these

| Exception | Thrown when |
|---|---|
| `OutOfMemoryError` | the heap is full and the garbage collector can't free anything you still reference |
| `StackOverflowError` | recursion goes too deep — no base case, or a call cycle |
| `AssertionError` | an `assert` statement failed (asserts only run with `-ea`) |
| `ExceptionInInitializerError` | a class's `static` initializer threw an exception |
| `NoClassDefFoundError` | a class was present at compile time but is missing or broken at run time |
| `UnsupportedClassVersionError` | the `.class` file was built by a newer Java than the running JVM |
| `UnsatisfiedLinkError` | a `native` method's library can't be found |
| `VerifyError` | a `.class` file is corrupted and failed bytecode verification |
| `InternalError` | something failed inside the JVM itself |
| `ThreadDeath` | legacy — thrown by the removed `Thread.stop()` API |

When one of these shows up, read the stack trace and fix the environment
(memory, classpath, Java version) — never wrap it in `catch`.

## Unchecked — RuntimeException and friends

### Null and wrong state

| Exception | Thrown when |
|---|---|
| `NullPointerException` | a method call or field access on `null` (`person.getName()` where `person` is null). Since Java 14 the message names the exact value |
| `IllegalStateException` | the object isn't in the right state for this call — iterator consumed, scanner closed, stream already read |
| `UnsupportedOperationException` | the object doesn't support the operation — e.g. `List.of(...).add(...)` |
| `IllegalThreadStateException` | a thread operation happened at the wrong time — e.g. `start()` called twice |
| `IllegalMonitorStateException` | `wait()`/`notify()` called without owning the object's lock |

### Wrong index, wrong math

| Exception | Thrown when |
|---|---|
| `ArithmeticException` | integer division by zero (`10 / 0`). A double `/ 0.0` doesn't throw — it quietly gives `Infinity` |
| `IndexOutOfBoundsException` | an index is outside a list's or string's bounds |
| `ArrayIndexOutOfBoundsException` | same, for arrays — `arr[10]` on a 3-element array |
| `StringIndexOutOfBoundsException` | same, for strings — `"hi".charAt(99)` |
| `NegativeArraySizeException` | `new int[-1]` |
| `ArrayStoreException` | storing the wrong type into an array — `Object[] a = new String[1]; a[0] = 42;` |

### Bad values and bad types passed in

| Exception | Thrown when |
|---|---|
| `IllegalArgumentException` | a method got a value it refuses — you can (and should) `throw` it yourself in your own checks |
| `NumberFormatException` | a string can't be parsed as a number — `Integer.parseInt("abc")` |
| `PatternSyntaxException` | a regex is malformed — `Pattern.compile("[a-")` |
| `IllegalFormatException` | `String.format` got arguments that don't match its format specifiers |
| `ClassCastException` | a downcast to the wrong type — `(Dog) someAnimal` where it's actually a `Cat` |

### Collections, stacks, and scanners

| Exception | Thrown when |
|---|---|
| `ConcurrentModificationException` | a collection changed while you were iterating it (fail-fast iterators) |
| `NoSuchElementException` | `Iterator.next()` with nothing left, or `Scanner.next()` at end of input |
| `EmptyStackException` | `Stack.pop()`/`peek()` on an empty stack |
| `InputMismatchException` | `Scanner.nextInt()` found a token that isn't an integer |

### Reflection, resources, and the rest (still unchecked)

| Exception | Thrown when |
|---|---|
| `SecurityException` | a security check denied the operation (mostly legacy security-manager code) |
| `EnumConstantNotPresentException` | an annotation references an enum constant that no longer exists |
| `TypeNotPresentException` | an annotation references a class that no longer exists |
| `MissingResourceException` | a resource bundle is missing the key you asked for |
| `RejectedExecutionException` | a task was submitted to a thread pool that has shut down |
| `CancellationException` | a `Future`/task you asked about was cancelled |

## Checked — the compiler forces you to decide

### java.lang

| Exception | Thrown when |
|---|---|
| `InterruptedException` | a sleeping or waiting thread was interrupted — handle it by rethrowing or restoring the interrupt flag |
| `CloneNotSupportedException` | `clone()` on a class that didn't opt in with `implements Cloneable` |
| `ClassNotFoundException` | reflective lookup found no class with that name — `Class.forName("com.foo.Bar")` |
| `InstantiationException` | reflection tried to instantiate an abstract class or interface |
| `IllegalAccessException` | reflection tried to reach private members without `setAccessible(true)` |
| `NoSuchMethodException` / `NoSuchFieldException` | reflective lookup found no member with that name |
| `ReflectiveOperationException` | common parent of the reflection failures above — one `catch` for all of them |

### java.io

| Exception | Thrown when |
|---|---|
| `IOException` | the general parent of every read/write failure — usually what you `catch` |
| `FileNotFoundException` | the file you tried to open doesn't exist (or can't be opened) |
| `EOFException` | the stream ended earlier than the data format promised |
| `NotSerializableException` | `writeObject` hit a class that doesn't implement `Serializable` |
| `InvalidClassException` / `StreamCorruptedException` | serialized data is incompatible or damaged |
| `ZipException` | a zip/gzip archive is damaged |

### java.net

| Exception | Thrown when |
|---|---|
| `MalformedURLException` | a URL string is malformed — `new URL("htp:/broken")` |
| `URISyntaxException` | `new URI(...)` got an illegal URI string |
| `UnknownHostException` | DNS lookup failed — the hostname doesn't exist |
| `ConnectException` | connection refused or timed out |
| `BindException` | the local port is already in use |
| `SocketException` | general socket failure — parent of the two above |

### java.sql

| Exception | Thrown when |
|---|---|
| `SQLException` | any database error — bad SQL, lost connection, bad parameter |
| `SQLTimeoutException` | a query exceeded its timeout |
| `SQLIntegrityConstraintViolationException` | primary key, foreign key, unique, or check constraint violated |

All `SQL*Exception` classes follow one pattern: catch `SQLException`, then read
`getErrorCode()`, `getMessage()`, and `getNextException()`.

### Parsing and background work

| Exception | Thrown when |
|---|---|
| `ParseException` | text doesn't match the expected format — `SimpleDateFormat.parse("yesterday")` |
| `DataFormatException` | compressed data doesn't match what the decompressor expected |
| `ExecutionException` | a background task failed — the real error is in `getCause()` |
| `TimeoutException` | `Future.get(...)` ran out of waiting time |

### java.security — crypto and keystores

| Exception | Thrown when |
|---|---|
| `NoSuchAlgorithmException` | the requested algorithm isn't available in this JVM |
| `KeyStoreException` | the keystore couldn't be loaded, or an entry is missing |
| `CertificateException` | a certificate is expired, malformed, or untrusted |

## Using this list

- **Catch the most specific type you can act on.** `FileNotFoundException`
  tells you to create the file; bare `IOException` doesn't. Order matters:
  subclasses before parents, or `javac` rejects the block.
- **`catch (Exception e)` as a last-resort outer wrapper is legal** — a common
  spot is the top of `main` — but never `catch (Error)`.
- **Never swallow.** An empty `catch` hides the bug forever; at least log it,
  or rethrow with a cause kept: `throw new AppException("failed", e)`.
- **Unchecked exceptions usually mean a code fix**, not a `catch`.

## Finding the rest

Your IDE lists every catchable type right after you type `catch (`. The JDK
has hundreds more (RMI, JNDI, AWT, sound, scripting…), but they all follow the
same shape: a class with a message, a stack trace, and often a wrapped cause.
Full reference: [Java 25 API docs](https://docs.oracle.com/en/java/javase/25/docs/api/).
