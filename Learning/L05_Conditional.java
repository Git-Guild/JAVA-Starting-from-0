//Todo: CONDITIONAL STATEMENTS

import java.util.Scanner;

public class L05_Conditional 
{
    public static void main(String[] args) 
    {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        if (age >= 18)
        {
            System.out.println("You are eligible to vote.");
        }
        else if(age<18) {
            System.out.println("You are not eligible to vote.");
        }
        else
        {
            System.out.println("Invalid age.");
        }

        System.out.print("Enter the time in hours: ");
        int time = scanner.nextInt();
        
        //& Ternary operator: (condition) ? value if true : value if false

        String message = (time < 12) ? "Good morning."
                : (time >= 12 && time < 18) ? "Good day."
                : "Good evening.";
        System.out.println(message);


        int  a = 200;
        int b = 300;
        int c = 400;
        int x = 500;
        int y = 600;
        int z = 550;
        if (a>b && a>c) // AND opeartor
        {
            System.out.println("A is the largest number.");
        }
        else if(b>a && b>c)
        {
            System.out.println("B is the largest number.");
        }
        else
        {
            System.out.println("C is the largest number.");
        }

        if (x>y || x>z) {
            System.out.println("OR operator atleast one is true.");
        }
        else{
            System.out.println("OR operator both are false.");
        }

        int d = 33;
        int e = 200;

        if (!(d > e)) {
        System.out.println("d is NOT greater than e");
        } // not operator

        //* switch statement: used to perform different actions based on different conditions but uses integer values only. It is used to avoid multiple if-else statements.
        int day = 6;
        switch (day) {
        case 1:
            System.out.println("Get back to work!");
            break;
        case 2:
            System.out.println("Today is Tuesday");
            break;
        case 3:
            System.out.println("Today is Wednesday");
            break;
        case 4:
            System.out.println("Today is Thursday");
            break;
        case 5:
            System.out.println("Today is Friday");
            break;
        default:
            System.out.println("HAPPY WEEKEND BABY!!"); // for no condtion match: DEFAULT
}
        scanner.nextLine();
        System.out.print("Enter your day: ");
        String Day = scanner.nextLine();

        switch(Day){
            case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> System.out.println("It is a weekday");
            case "Saturday", "Sunday" -> System.out.println("It is a WEEKEND!!");
            default -> System.out.println("Invalid day");
        } //~Enhacned switch cases

        scanner.close();
    }
}
