import java.util.Scanner;
public class Factorial {
    public static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
    void main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number for factorial: ");
        int n = sc.nextInt();
        System.out.println("The factorial of " + n + " is " + factorial(n));
        sc.close();
    }
}
