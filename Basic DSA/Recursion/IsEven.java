//TODO: Finding if a number enetred by user is even or add using indirect recursion
import java.util.Scanner;

/*
! In indirect recursion, we call a function inside another function.
* We will create 2 functions Even and Odd they will call each other until one gets an answer.
*/

public class IsEven {
    public static boolean isEven(int n) {
        if (n == 0) {
            return true;
        }
        return isOdd(n - 1); //! calls another function again and again (INDIRECT RECURSION)
    }
    public static boolean isOdd(int n){
        if(n==0){
            return false;
        }
        return isEven(n-1); // same as above
    }
    void main(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();
        System.out.println(n + " is even? " + isEven(n));
        System.out.println(n + " is odd? " + isOdd(n));
    }
}
