//! WHY THERE IS NO `class` IN THIS FILE
//   Older Java forced every file to look like this:

//       public class Basics {
//           public static void main(String[] args) {
//               ...
//           }
//       }

//   Java 25 lets you delete all of that. You just write
//   `void main() { ... }` and Java wraps the file in a hidden
//   class for you. Less typing, same result.

// Brings in the standard Java tools (lists, math, dates...).
// Java 25 already does this automatically in files like this
// one, so the line is optional - it is here so you can see it.
import module java.base;

final String APP_NAME = "Java 25 Demo";   // final = never changes

int visitCount = 0;

// 1 to visitCount so we can count how many times it ran.
String welcome(String name) {
    visitCount++;
    return "Welcome to %s, %s! (visit #%d)".formatted(APP_NAME, name, visitCount);
}

// A "switch" picks one path based on a value - like a stack of
// if/else lines, but tidier. This version is an "expression":
// it hands a value back (note the `return`) instead of only
// printing something. No `break` lines are needed.
String dayType(DayOfWeek day) { // java in itself has a dayOfWeek enum
    return switch (day) {
        case SATURDAY, SUNDAY -> "weekend";
        case FRIDAY           -> "almost weekend";
        default               -> "weekday";
    };
}

// The starting point. Java looks for `main` and runs whatever
// is inside it. In older Java this line had to read
//* `public static void main(String[] args)`; plain `void main()`
// works now. (If you ever need command-line arguments, use
// `void main(String[] args)` instead.)
void main() {

    //* IO.println prints a line and moves to the next line.
    // IO.print prints without moving to the next line.
    //! (Older Java: System.out.println.)
    IO.println("=== 1. IO class ===");
    IO.print("IO.print does not add a newline. ");
    IO.println("IO.println does.");

    //! Asking the user for input
    //* IO.readln prints the question, then waits for the user to
    // type a line and press Enter. (Older Java: a Scanner, which
    // took several lines of code.)
    String name = IO.readln("What's your name? ");
    if (name == null || name.isBlank()) {
        name = "stranger";
    }
    IO.println(welcome(name.strip())); // no whitespaces

    //! `var`: let Java work out the type 
    // List.of(...) makes a list that cannot be changed.
    // Map.of(...) makes named values you can look up (Ana -> 21).
    // Instead of writing the full type, you may write `var` and
    // let Java copy it from the right-hand side. The variable is
    // still fully typed - `scores` really is a list of integers.
    IO.println("\n=== 2. Lists and maps ===");
    var scores = List.of(88, 92, 75, 100, 61);
    var ages   = Map.of("Ana", 21, "Ben", 25);
    IO.println("Scores: " + scores + ", max = " + Collections.max(scores));
    IO.println("Ana's age: " + ages.get("Ana"));

    //! The underscore `_`
    // Sometimes Java forces you to name something you will never
    // use (like the current item in a loop). Name it `_`: it
    // clearly means "I am ignoring this on purpose".
    IO.println("\n=== 3. Unnamed variables (_) ===");
    int count = 0;
    for (var _ : scores) {   // read every item, but only count them
        count++;
    }
    IO.println("Counted " + count + " scores without naming the loop variable.");

    //! try/catch means "attempt this, and if it fails, run this
    // block instead of crashing". The text is not a number, so
    // turning it into one fails, and we catch that.
    try {
        Integer.parseInt("not a number");
    } catch (NumberFormatException _) {   // we don't need the error details
        IO.println("Caught a bad number (exception variable ignored).");
    }

    // Walk through the map, printing only the names.
    ages.forEach((key, _) -> IO.println("Key only: " + key));

    //! Text blocks: strings spanning several lines
    // Three quotes let you write multi-line text exactly as
    // shown - no \n characters and no "..." + "..." stitching.
    // The closing """ sets the left margin, so the extra
    // indentation is removed automatically.
    IO.println("\n=== 4. Text blocks ===");
    String json = """
            {
              "app": "%s",
              "visitor": "%s", 
              "scores": %s
            }
            """.formatted(APP_NAME, name.strip(), scores); //* %s is used for string interpolation (string substitution)
    IO.print(json);

    // The switch from dayType()
    IO.println("\n=== 5. Switch expression ===");
    for (DayOfWeek d : List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)) {
        IO.println(d + " is: " + dayType(d));
    }

    //! Handy things strings can do
    IO.println("\n=== 6. Modern String helpers ===");
    String messy = "   Hello, Java 25!   ";
    IO.println("[" + messy.strip() + "]");    //* strip() cuts spaces at both ends
    IO.println("blank? " + "   ".isBlank());   //* only spaces? isBlank() says true
    IO.println("-".repeat(20));                //* repeat the text 20 times
    //* lines() cuts the text at every newline; each line then gets changed to upper case and printed.
    "line one\nline two\nline three".lines()
            .map(String::toUpperCase)
            .forEach(IO::println);

    //! Filtering a list
    // stream() starts a series of steps on a list: keep only the
    // even numbers (filter), then collect the results into a new
    // list (toList).
    var evens = scores.stream().filter(s -> s % 2 == 0).toList();
    IO.println("\nEven scores: " + evens);

    IO.println("\nDone. Next file: Java25Tour.java");
}
