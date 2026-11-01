import java.util.ArrayDeque;
import java.util.Deque;

// adding *First* at end of a deque function name will do the same as the default function name i.e. addFirst == add

void main() {
    Deque<Integer> deque = new ArrayDeque<>();

    // 1. ADDING (Both add to the BACK)
    deque.add(10);       // Inherited Queue method
    deque.addLast(20);   // Explicit Deque method
    System.out.println("After adding to BACK: " + deque); // 

    deque.offer(30);     // (same as add)
    deque.offerLast(40); // (same as addLast)

    // 2. PEEKING
    System.out.println("peek()      [FRONT]: " + deque.peek());
    System.out.println("peekLast() [BACK]: " + deque.peekLast());

    // 3. REMOVING
    System.out.println("Removed via poll():      " + deque.poll());
    System.out.println("Removed via pollLast(): " + deque.pollLast());
    
    System.out.println("Final Deque State: " + deque);
}
