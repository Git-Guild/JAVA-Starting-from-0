//TODO: To learn exceptions in JAVA  (theory + full hierarchy in this folder's README.md)


//! An exception is an event, which occurs during the execution of a program, that disrupts the normal flow of the program's instructions. (runtime error)

/*
* When an error happens in a method, it creates an object and send it to Runtime system
* This is called Exception object and it contains all information about the error. This process is called *Throwing an Exception*
* (In Java: `throw new SomeException("msg");` — you always throw an OBJECT, never a bare number/string like C++ could)
*/

/*
! Try block contains the code that might throw an error
! Catch block, if the error in Try happens it will look for error handler in catch block related to that try (like an if else statement)
* Flow: normal code runs -> something throws -> the try block STOPS at that line ->
* JVM looks for a matching catch -> handler runs -> execution continues AFTER the try/catch.
*
* The thrown object carries the error class, the message, and the STACK TRACE
* (the path of method calls that led here — Java's biggest debugging advantage over C++).
*
* Part 2 of this phase: Error_Handling.java (finally, checked vs unchecked, custom exceptions)
*/

public class TryAndCatch {

    // a method that CAN throw: it divides, but refuses to divide by zero
    static int divide(int a, int b) {
        if (b == 0)
            // 'throw' stops this method immediately and hands the exception
            // object to the nearest matching catch above it
            throw new ArithmeticException("Division by zero error!");   // built-in unchecked exception
        return a / b;
    }

    public static void main(String[] args) {

        // 1) the basic try / catch
        try {
            System.out.println("10 / 2 = " + divide(10, 2));
            System.out.println("10 / 0 = " + divide(10, 0));   // throws! jumps to catch
            System.out.println("This line will never print!"); // skipped — execution left the try
        }
        catch (ArithmeticException e) {   // catch blocks match by TYPE
            // getMessage() = the text, printStackTrace() = the method-call path to the failure
            System.out.println("[CAUGHT]: " + e.getMessage());
            e.printStackTrace(); // the long error you are going to see in terminal
        }
        System.out.println("\nProgram keeps running after the catch.\n");

        // 2) catching a built-in runtime error
        try {
            int[] scores = {88, 92, 75};
            System.out.println("Trying to read index 5...");
            System.out.println(scores[5]);   // ArrayIndexOutOfBoundsException is thrown here
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[CAUGHT]: " + e);
            System.out.println("[CAUGHT]: " + e.getMessage());
        }
        System.out.println();

        // 3) catching by a broader type (specific catch blocks come first — see README)
        try {
            Integer.parseInt("abc");   // NumberFormatException: "abc" is not a number
        }
        catch (NumberFormatException e) {
            System.out.println("[CAUGHT]: Cannot convert 'abc' to int -> " + e.getClass().getSimpleName());
        }
        System.out.println();

        // 4) throwing your own exception object
        try {
            throw new Exception("Something custom went wrong!");   // checked exception, must be caught
        }
        catch (Exception e) {
            System.out.println("[CAUGHT]: " + e.getMessage() + " (class: " + e.getClass().getName() + ")");
        }

        // glossary: throw = launch now | throws = method signature promise |
        // catch = intercept by type | stack trace = e.printStackTrace() breadcrumbs
    }
}
