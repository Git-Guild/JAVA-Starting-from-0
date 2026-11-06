//TODO: Error_Handling.java — exceptions end-to-end (hierarchy + rules in README.md)
// Basics file: TryAndCatch.java

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// The keywords: try / throw / catch / finally, plus Java's own `throws`
// (declare on a method signature). Differences from C++: you always throw an
// OBJECT, checked exceptions are compiler-enforced, and finally always runs.

// custom CHECKED exception: every caller must catch it or declare `throws`
class InsufficientFundsException extends Exception {
    private final double deficit;   // extra data about the failure

    InsufficientFundsException(double missingAmount) {
        super("Error 402: Insufficient funds for transaction!");   // message goes to super
        this.deficit = missingAmount;
    }

    double getDeficit() { return deficit; }
}

// custom UNCHECKED exception: no `throws` anywhere — treated like a bug
class NegativeWithdrawalException extends RuntimeException {
    NegativeWithdrawalException(double amount) {
        super("Withdrawal amount must be positive, got: " + amount);
    }
}

public class Error_Handling {

    // 1) basic throw: division by zero
    static int divide(int a, int b) {
        if (b == 0)
            throw new ArithmeticException("Division by zero error!");   // built-in, unchecked
        return a / b;
    }

    // 2) CHECKED exception: `throws` pushes the decision to the caller
    static int countLines(String path) throws IOException {
        // (without `throws IOException` above, this method wouldn't compile)
        BufferedReader br = new BufferedReader(new FileReader(path));   // may throw FileNotFoundException
        int lines = 0;
        while (br.readLine() != null) lines++;
        br.close();   // manual close — see try-with-resources below for the right way
        return lines;
    }

    // 3) a method that declares a checked exception and can also throw an unchecked one
    static void doBusinessWork(double balance, double withdrawal) throws InsufficientFundsException {
        if (withdrawal < 0)
            throw new NegativeWithdrawalException(withdrawal);   // unchecked: no `throws` needed
        if (withdrawal > balance)
            throw new InsufficientFundsException(withdrawal - balance);   // checked: declared above
        System.out.println("[OK] Withdrew $" + withdrawal + ", balance left: $" + (balance - withdrawal));
    }

    public static void main(String[] args) {

        // 1) try / catch (basics)
        System.out.println("1) try / catch (basics)");
        try {
            System.out.println("10 / 2 = " + divide(10, 2));
            System.out.println("10 / 0 = " + divide(10, 0));   // throws
            System.out.println("never prints");                // skipped
        } catch (ArithmeticException e) {                       // matched by TYPE
            System.out.println("[CAUGHT]: " + e.getMessage());
            e.printStackTrace();   // full stack trace: which call path led to the throw
        }
        System.out.println("execution continues after try/catch\n");

        // 2) multi-catch: one block, several types (they must NOT be related to each other)
        System.out.println("2) MULTI-CATCH (one block, several types)");
        try {
            String[] names = { "Raj", "Aman" };
            System.out.println(names[5]);                     // ArrayIndexOutOfBounds
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) { //* | = OR
            System.out.println("[CAUGHT multi]: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
        }
        System.out.println();

        // 3) checked exception: the compiler forces this catch (or a `throws` on main)
        System.out.println("3) CHECKED EXCEPTION: catch it here");
        try {
            countLines("this_file_definitely_does_not_exist.txt");
        } catch (IOException e) {
            System.out.println("[CAUGHT checked]: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        System.out.println();

        // 4) custom exceptions (the 402 scenario)
        System.out.println("4) CUSTOM EXCEPTIONS (402 scenario)");
        try {
            doBusinessWork(100.0, 500.0);
        } catch (InsufficientFundsException e) {                // checked -> MUST be caught/declared
            System.out.println("[CAUGHT 402]: " + e.getMessage()
                    + " | deficit data carried in the object: $" + e.getDeficit());
        }
        try {
            doBusinessWork(100.0, -50.0);                       // fires the UNCHECKED one...
        } catch (NegativeWithdrawalException e) {
            System.out.println("[CAUGHT unchecked custom]: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            // ...but javac still requires this catch: doBusinessWork DECLARES the checked
            // exception, so every call site must handle it — even one you "know" won't fire
            System.out.println("[CAUGHT checked]: never actually thrown here, but required by javac");
        }
        System.out.println();

        // 5) finally: runs no matter what
        System.out.println("5) finally: runs NO MATTER WHAT");
        System.out.println(demoFinallyReturning());
        System.out.println(demoFinallyOnException());
        System.out.println();

        // 6) try-with-resources: finally, automated (Java 7+)
        // Earlier you needed to add a finally block after try and catch blocks to close resources, but now it is not necessary
        System.out.println("6) try-with-resources: finally, automated (Java 7+)");
        // anything declared in try(...) is closed automatically, even on exception
        try (BufferedReader br = new BufferedReader(new FileReader("nope.txt"))) {
            System.out.println(br.readLine());
        } catch (IOException e) {
            System.out.println("[CAUGHT]: resource problem: " + e.getClass().getSimpleName());
        }
        System.out.println("(br was closed for us — no close() line, no finally needed)\n");

        // 7) Java enforces specific-before-broad: catch(Exception) BEFORE a specific
        // catch is a compile error ("exception is already caught")
        System.out.println("7) catching broadest-first would be a MISTAKE");
        System.out.println("Java rejects catch(Exception) placed before catch(ArithmeticException).");
    }

    // finally demos: the block runs before the method actually returns,
    // and it runs after a catch too — cleanup is guaranteed
    static String demoFinallyReturning() {
        try {
            return "try returned: I am going home...";
        } finally {
            System.out.println("  [finally #1] runs even though try says return");
        }
    }

    static String demoFinallyOnException() {
        try {
            throw new RuntimeException("boom inside try");
        } catch (RuntimeException e) {
            return "caught in catch: " + e.getMessage();
        } finally {
            System.out.println("  [finally #2] runs after catch too — cleanup is guaranteed");
        }
    }
}
