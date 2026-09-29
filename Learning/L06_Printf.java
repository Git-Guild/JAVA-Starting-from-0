//TODO: Understanding printf

public class L06_Printf {
    public static void main(String[] args) {
        
        String name = "Yuvraj";
        char firstLetter = 'Y';
        int age = 19;
        double cgpa = 8.4567;
        boolean isStudent = true;

/*
        * printf: Print formatted string to console
        * Format specifier: %[flags][width][.precision]conversion-character
        * Conversion Characters:
        *   %s: String
        *   %c: Character
        *   %d: Integer (byte, short, int, long)
        *   %f: Floating-point number (float, double)
        *   %b: Boolean
*/
        System.out.printf("My name is %s\n", name);
        System.out.printf("My name starts with %c\n", firstLetter);
        System.out.printf("My age is %d\n", age);
        System.out.printf("My CGPA is %f\n", cgpa);
        System.out.printf("I am a student: %b\n", isStudent);

        System.out.println("\n--- [Width] ---");
        //& Sets the minimum number of characters to be printed.
        //& If the value is shorter, it's padded. Default is right-justified.
        System.out.printf("Hello, [%10s]\n", name);
        System.out.printf("Age: [%5d]\n", age);

        System.out.println("\n--- [Precision] ---");
        //& For floating-point numbers, it sets the number of digits after the decimal.
        System.out.printf("My CGPA with precision: [%.2f]\n", cgpa);

        System.out.println("\n--- [Flags] ---");
        //& - : left-justify
        System.out.printf("Hello, [%-10s] (left-justified)\n", name);

        //& + : output a plus or minus sign for a numeric value
        int temperatureChange = 2;
        System.out.printf("Temperature change: [%+d]\n", temperatureChange);

        //& 0 : numeric values are zero-padded
        System.out.printf("Padded age: [%05d]\n", age);

        //& , : grouping separator for numbers
        int largeNumber = 1000000;
        System.out.printf("A large number: [%,d]\n", largeNumber);

        System.out.println("\n--- [Combining Width, Precision, and Flags] ---");
        //& A positive number, right-justified in a field of 20 characters,
        //& with a comma separator, and 2 decimal places.
        double balance = 1234567.897;
        System.out.printf("Your account balance is: [%+,20.2f]\n", balance);

        System.out.println("\n--- [Argument Index] ---");
        //& You can specify the order of arguments. 1$ refers to the first argument, 2$ to the second, etc.
        System.out.printf("My name is %1$s, and I am %2$d. Yes, %1$s is my name.\n", name, age);

    }

}
