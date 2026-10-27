import java.util.ArrayDeque;
import java.util.Deque; // Deque can be both LIFO and FIFO, we will use push() and pop() for LIFO Stack
void main(){
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(5); // add elements to the stack
    stack.push(10);
    stack.push(15);
    stack.push(25);
    stack.push(19);

    System.out.println("Total elements in stack: " + stack.size()); // check the size of the stack

    System.out.println("Element at the top: " + stack.peek()); // check the top element

    System.out.println("Popping the top element...");
    stack.pop(); // remove the top element

    System.out.println("New element at the top: " + stack.peek());

    System.out.println("Remaining elements (Top -> Bottom): ");
    while (!stack.isEmpty()) {
        System.out.print(stack.pop() + " "); // we can print out the popped element using pop()
    }

}

