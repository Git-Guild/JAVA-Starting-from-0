import java.util.Scanner;

class Stack {
    private int size; // The maximum number of elements the stack can hold
    private int top = -1; // Pointer/Index to track the top element. Starts at -1 because the stack is initially empty
    private int[] stack;

    public void setSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter size of stack: ");
        size = scanner.nextInt();
        stack = new int[size]; // Initialize the array with the user-defined size
    }

    // Method to add an element to the top of the stack (LIFO)
    public void push(int val) {
        // OVERFLOW CHECK
        // Indexing is 0-indexed, so the final valid slot is at size - 1.
        // If top is already at the last index, the stack is full.
        if (top >= size - 1) {
            System.out.println("OVERFLOW: Cannot push " + val);
        } else {
            top++; // Move the top pointer up to the next available empty slot
            stack[top] = val; // Store the value at the newly updated top position
            System.out.println("Pushed " + val);
        }
    }

    // Method to remove the most recently added element from the stack
    public void pop() {
        // UNDERFLOW CHECK
        // If top is still -1, no elements have been successfully pushed.
        if (top == -1) {
            System.out.println("UNDERFLOW: Stack is empty");
        } else {
            // Read and display the item at the current top before removing it
            System.out.println("Popped " + stack[top]);
            top--; // Move the top pointer down
        }
    }

    public void display() {
        if (top == -1) {
            System.out.println("Stack is empty");
            return;
        }
        System.out.print("Stack elements (Top -> Bottom): ");
        for (int i = top; i >= 0; i--) {
            System.out.print(stack[i] + " ");
        }
        System.out.println();
    }
}


    void main() {
        Stack s = new Stack();
        s.setSize();
        
        s.push(5);
        s.push(10);
        s.push(15);
        s.push(25);
        s.push(19);
        s.push(9); // If you enter a size smaller than 6, this will trigger Overflow
        
        s.display();
        s.pop();
        s.display();
    }

