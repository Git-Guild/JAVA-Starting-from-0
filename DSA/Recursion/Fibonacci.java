import java.util.Scanner;

public class Fibonacci {
    public static int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }
    void main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number for Fibonacci: ");
        int n = sc.nextInt();
        System.out.println("The " + n + "th fibonacci number is " + fib(n));
        sc.close();
    }
}
