//* Why is there no "class" and no long main line in this file?

// In old Java you had to write this before you could even print "Hello":
//     public class Basics {
//         public static void main(String[] args) {
//             System.out.println("Hello");
//         }
//     }
// Beginners had to learn five strange words on day one.

//* Java 25 removes all that. You just write your code. Behind the scenes,
// Java puts your code inside a hidden class and names it after the file.
//! So the file name must start with a letter. Basics.java works, but
// 1Basics.java does not.

//& An import is a way to tell Java "I want to use tools from this place".
// Java comes with thousands of ready-made tools (lists, dates, math) kept
// in groups. "import module java.base" brings in the most useful groups
// with one line, so you do not need one line for every tool.
// In a file like this, Java already does it for you. The line is here only
// so you can see what it looks like. You could delete it.
import module java.base;

/// `final` means "fill the box once and never change it".
final String APP_NAME = "Java 25 Demo";

// A variable written outside any method, like this one, can be used by every method in the file.
int visitCount = 0;

// The "%s" and "%d" in the text are empty spots. .formatted(...) fills
// them in order: %s takes text, %d takes a whole number.
String welcome(String name) {
    visitCount++;   //& ++ means "add one"
    return "Welcome to %s, %s! (visit #%d)".formatted(APP_NAME, name, visitCount);
}

// DayOfWeek is an "enum". An enum is a fixed list of allowed values. Here
// the list is MONDAY, TUESDAY and so on up to SUNDAY. Nothing else is
// allowed. Java comes with this one. We make our own enum in the next file 'ModernTypes.java'.

//* A "switch" looks at one value and picks the matching line. This version
// is called a switch expression, because it gives back a result you can
// keep. Each line is: when it is this -> then give back that.
// Old switches needed a "break" after every line. This one does not.
String dayType(DayOfWeek day) {
    return switch (day) {
        case SATURDAY, SUNDAY -> "weekend";
        case FRIDAY           -> "almost weekend";
        default               -> "weekday";    //& default = everything else
    };
}

//* main is where your program starts. When you run the file, Java finds
// main and runs the lines inside it from top to bottom.
// `void` means main gives nothing back. (If you ever want words typed after
// the command, write  void main(String[] args)  and they arrive in args.)
void main() {

    //* IO is a small helper that comes with Java. You do not import it.
    //   IO.println(...)  shows text, then moves to the next line.
    //   IO.print(...)    shows text and stays on the same line.
    //   IO.readln("question")  shows the question, waits for the user to
    //                    type a line and press Enter, then gives it back.
    // Why it is nice: before this, you needed System.out.println to show
    // text, and a whole Scanner setup to read it. IO does both in one word.
    //! You must write "IO." in front. A bare println will not work.
    IO.println("[1] Showing text");
    IO.print("print stays on the line. ");
    IO.println("println moves down.");

    // null means "nothing at all". readln gives back null when there is no
    // more input to read. Blank means empty or only spaces.
    // || means "or". So the next line asks: is it null, or is it blank?
    // If yes, we use "stranger" instead, so the program never breaks.
    String name = IO.readln("What's your name? ");
    if (name == null || name.isBlank()) {
        name = "stranger";
    }
    //& strip() removes extra spaces from the start and the end.
    IO.println(welcome(name.strip()));

    //* A list holds many values in order. A map holds pairs, like a phone
    // book: you look up a key (a name) and get its value (a number).
    // List.of and Map.of make ones that cannot be changed afterwards.

    // `var` tells Java "you work out the type yourself". Java still knows
    // the exact type, so you lose no safety. You just type less.
    IO.println("\n[2] Lists and maps");
    var scores = List.of(88, 92, 75, 100, 61);
    var ages   = Map.of("Ana", 21, "Ben", 25);
    IO.println("Scores: " + scores + ", highest = " + Collections.max(scores));
    IO.println("Ana's age: " + ages.get("Ana"));

    //* A loop repeats code. This one runs once for every score, and "score"
    // is the name for the current one.
    IO.println("\n[3] Loops and the _ name");
    int total = 0;
    for (var score : scores) {
        total += score;    //& += means "add this to what is already there"
    }
    IO.println("Total of all scores: " + total);

    //* Sometimes a loop needs a name but you never use it. Here we only want
    // to count, so we do not care what each score is. Write _ and Java
    // understands "I do not need this". It also tells the next reader that
    // the name is not used, which makes the code easier to read.
    int count = 0;
    for (var _ : scores) {
        count++;
    }
    IO.println("Number of scores: " + count);

    //* try / catch handles problems without crashing. Java tries the code in
    // the try part. If it breaks, Java jumps to the catch part and goes on.
    // "not a number" can never turn into a number, so it breaks.
    // We do not need the details of the problem, so we write _ again.
    try {
        Integer.parseInt("not a number");
    } catch (NumberFormatException _) {
        IO.println("That text is not a number, but the program kept going.");
    }

    //* The -> arrow below makes a lambda. A lambda is a tiny recipe with no
    // name that you hand to another method. forEach runs your recipe once
    // for every pair in the map.
    // (key, _) means: I get two things, a key and a value, and I only use
    // the key. The order of a Map.of can change from run to run.
    ages.forEach((key, _) -> IO.println("Person: " + key));

    //* A text block is text over many lines, written just as you want it
    // to look. Start and end with three quote marks. No \n is needed to
    // break lines. Java removes the spaces that sit to the left of the
    // closing three quotes.
    IO.println("\n[4] Text block");
    String card = """
            {
              "app": "%s",
              "visitor": "%s",
              "scores": %s
            }
            """.formatted(APP_NAME, name.strip(), scores);
    IO.print(card);

    IO.println("\n[5] Switch");
    for (DayOfWeek day : List.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)) {
        IO.println(day + " is: " + dayType(day));
    }

    //* A few handy things you can do with text.
    IO.println("\n[6] Text helpers");
    IO.println("[" + "   padded   ".strip() + "]");    // cut the spaces at both ends
    IO.println("only spaces? " + "  no ".isBlank());     // false - "no" is not blank
    IO.println("-".repeat(20));                         // copy a text many times

    //* lines() cuts text at every new line. A stream is like a belt in a
    // factory: items ride along and pass through steps one by one.
    // Here the step is: change every line to capital letters. Then forEach
    // shows each one. `IO::println` is a short way to write
    // line -> IO.println(line)
    "line one\nline two".lines()
            .map(String::toUpperCase)
            .forEach(IO::println);

    //* The same belt idea on our scores. filter keeps only the items that
    // pass the test. The test s % 2 == 0 asks: does dividing by 2 leave
    // nothing left over? (% gives the leftover.) If yes, the score is even.
    // toList then collects what is left into a new list.
    var evens = scores.stream().filter(s -> s % 2 == 0).toList();
    IO.println("\nEven scores: " + evens);

    IO.println("\nDone. Next: ModernTypes.java");
}
