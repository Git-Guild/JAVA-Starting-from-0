//TODO: Understanding syntax of JAVA

public class L01 { // The name of the class(file)
    public static void main(String[] args) { // The main method
        System.out.println("Hello world!");

        //! Variable declaration and initialization

        //* Primitive data types = int, double, char, boolean */

        int age;
        age = 20;
        System.out.println("My age is " + age);
        double pi = 3.14;
        double radius = 5.5;
        double area = pi * radius * radius;
        System.out.println("The area of the circle is " + area);
        char grade = 'A';
        char dollarSign = '$';
        System.out.println("USA currency is " + dollarSign);
        System.out.println("My grade is " + grade);
        boolean isStudent = true;

        if(isStudent) { //& if-else is a conditional statement: if(condition) { //code to be executed if condition is true } else { //code to be executed if condition is false }
            System.out.println("I am a student.");
        }
        else {
            System.out.println("I am not a student.");
        }

        final char gender = 'M'; //& final keyword is used to declare a constant variable whose value cannot be changed
        System.out.println("My gender is " + gender);

        float studentFee = 2500.50f; //& float data type is used to store decimal values with single precision. f in last indicates that it's a float literal and without it the program will not compile
        System.out.println("Student fee is " + studentFee);

        var temperature = 36.5; //& var keyword is used for local variable type inference. The compiler infers the type of the variable from the assigned value. very good for DSA
        System.out.println("Current temperature is " + temperature + " degree Celsius.");

        int arr[]= {1,2,3,4,5}; //& arr is nothing but a list
        System.out.println("First element of array is " + arr[0]);
        System.out.println("Length of array is " + arr.length);
        System.out.println("Last element of array is " + arr[arr.length - 1]);
        System.out.println("Array elements are: ");

        for(int i=0; i<arr.length; i++) { //& for loop to iterate through the array: for(initialization; condition; increment/decrement)
            System.out.println(arr[i]);
        }

    }
}