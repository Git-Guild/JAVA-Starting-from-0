//TODO: Take input from user for a length of string/characters and also for the length of combination, then generate all possible combinations (no two combinations should be same or be palindrome of any other one combination)

/*
Imagine the user enters 12345 as string and 2 as the combination length. The output should be:
12 13 14 15 23 24 25 34 35 45
if the combination length is 3, the output should be:
123 124 125 134 135 145 234 235 245 345 
*/

/*
 ! How I Came Up With This Solution
 * 
 * 1. The Dilemma (Why nested loops aren't enough):
 *    When I wanted combinations of 2 characters (k=2), I naturally thought 
 *    of using two nested loops. But what if the user asks for k=3, k=4, 
 *    or k=10? I can't write 10 nested loops by hand! I needed a way to 
 *    generate loops dynamically based on whatever `k` the user types.

 * 2. The Breakthrough (Recursion as a Multi-Layered Loop):
 *    I realized recursion can act like loops inside loops. Each time the 
 *    function calls itself, it steps one level deeper to pick the next 
 *    character, until our string hits length `k`.

 * 3. Moving Forward (`i + 1`):
 *    To prevent getting duplicates (like "21" when we already have "12") 
 *    or reusing the same letter, I made sure the next choice always starts 
 *    strictly after our current index. Passing `i + 1` ensures we only 
 *    look forward in the string.

 * 4. The Magic of Backtracking (`pop_back()`):
 *    Once we hit length `k` (like "125"), we save it. But how do we get 
 *    back to "12" to try "124"? That's where `pop_back()` comes in! 
 *    It chops off the last character, allowing us to step back, try a 
 *    different option, and keep exploring.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

void generateCombinations(String str, int k, int start, StringBuilder current, List<String> arr) {// Strings are immutable, so we need to use a StringBuilder to change the string
    // Base case: If the current combination length equals k, save it
    if (current.length() == k) {
        arr.add(current.toString());
        return; //exits recursion loop
    }

    // Loop through the string starting from the 'start' index to avoid duplicates
    for (int i = start; i < str.length(); i++) {
        current.append(str.charAt(i)); // Push: add the character in our StringBuilder
        
        // Recurse with i + 1 to ensure we only look forward
        // 12345, i got 1, i + 1 got 12, i + 3 got 123 and when it is equal to k in length, our base case will stop it from running
        generateCombinations(str, k, i + 1, current, arr);
        
        current.deleteCharAt(current.length() - 1); // Pop: backtrack
        // we made 123 now we want 124, this line pops 3 making it 12 again and then the next iteration of loop will add 124, same happens when all combos for 12- are done it goes to 13- and so on
    }
}

void main() {

    try (var scanner = new Scanner(System.in)) {
        System.out.print("Enter your characters: ");
        String str = scanner.next();

        System.out.print("Enter combination length (e.g., 2, 3, 4): ");
        int k = scanner.nextInt();

        List<String> arr = new ArrayList<>();
        
        // Start the recursion
        generateCombinations(str, k, 0, new StringBuilder(), arr);

        // Print the generated combinations
        System.out.print("Generated pairs: ");
        for (String pair : arr) {
            System.out.print(pair + " ");
        }
        System.out.println();
    }
}
