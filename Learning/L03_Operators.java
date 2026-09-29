//TODO: Arithmetic Operators

public class L03_Operators {
    public static void main(String[] args) {
        int x, y;
        x = 10;
        y = 20;
        System.out.println("Sum is: " + (x + y));
        System.out.println("Subtraction is: " + (x - y));
        System.out.println("Multiplication is: " + (x * y));
        System.out.println("Division is: " + (x / y));
        System.out.println("Modulus is: " + (x % y)); //& modulus operator gives the remainder of the division
        System.out.println("Increment is: " + (++x));
        System.out.println("Decrement is: " + (--y));
        
        int a,b;
        
        a = 15;
        b = 10;
        System.out.println("a += b: " + (a += b));
        System.out.println("a -= b: " + (a -= b));
        System.out.println("a *= b: " + (a *= b));
        System.out.println("a /= b: " + (a /= b));
        System.out.println("a %= b: " + (a %= b));

        System.out.println("New a is: " + (a));
        System.out.println("New b is: " + (b));
        System.out.println("New x is: " + (x));
        System.out.println("New y is: " + (y));
        
        System.out.println((x/y)+(9*a/b)-(a*x-b*y)); //& PEMDAS rule

        System.out.println(Math.max(a, b));
        System.out.println(Math.min(a, b));
        System.out.println(Math.sqrt(100));
        System.out.println(Math.abs(-100));
        System.out.println(Math.pow(2,3));
        System.out.println(Math.round(5.6));
        System.out.println(Math.floor(5.6));
        System.out.println(Math.ceil(5.6));
        System.out.println(Math.random());
        int randomNum = (int) Math.random() * 101; //& a double value from 0.0 to 1.0 but int makes it 0 only
        int randomNum1 = (int) (Math.random() * 101); //& a double value from 0.0 to 1.0 but *101 makes it 0 to 100
        System.out.println(randomNum); 
        System.out.println(randomNum1);

    }
}
