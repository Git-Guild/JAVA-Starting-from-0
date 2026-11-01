//TODO:  Circular array implementation of a Double-Ended Queue (Deque)

/*
! CIRCULAR ARRAY DEQUE ALGORITHM
* A Double-Ended Queue (Deque) that wraps indices using modulo arithmetic.

* Key Components:
* - front: Index of the first element.
* - rear: Index of the NEXT empty slot after the last element.
* - currentSize: Tracks item count to distinguish between empty and full states.

* Modulo Wrap-Around Formulas:
* - Move Right (Clockwise): index = (index + 1) % capacity
* - Move Left (Counter-Clockwise): index = (index - 1 + capacity) % capacity
*/

class Deque {
    private final Integer[] deque; // using Integer (wrapper) for null use to release garbage data in pop function, with int we can't delete directly using null
    private int front;
    private int rear;
    private final int capacity;
    private int currentSize;

    public Deque(int size) {
        this.capacity = size;
        this.deque = new Integer[size];
        this.front = 0; // start at 0 for empty array
        this.rear = 0;
        this.currentSize = 0;
    }

    public boolean isFull() {
        return currentSize == capacity;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    // Insert at the back: Insert at rear, then shift rear right
    public void push_back(int val) {
        if (isFull()) {
            System.out.println("OVERFLOW: Cannot push back " + val);
            return;
        }
        deque[rear] = val;
        rear = (rear + 1) % capacity; // update after adding value
        // % capacity gives the same result rear = (rear + 1) but it also handles if the rear is at the last index i.e. 4 (in case of 5 size) so 5%5 becomes 0 making it circular
        currentSize++;
        System.out.println("Pushed back: " + val);
    }

    // Insert at the front: Shift front left, then insert at front
    public void push_front(int val) {
        if (isFull()) {
            System.out.println("OVERFLOW: Cannot push front " + val);
            return;
        }
        front = (front - 1 + capacity) % capacity; // update before adding, % handles -ve values as well, if the array is empty it adds in 4th (size = 5) but since it is circular, it is also 1st index, we'll handle this in display
        deque[front] = val;
        currentSize++;
        System.out.println("Pushed front: " + val);
    }

    /*
    ! Why different order of updating pointer and adding value?
    * Pushing from back: Add value, then shift rear right
    * Pushing from front: Shift front left, then add
    * This is because the rear is always at back where we can add, and the front is always to be shifted before the last front one
    */

    /*
    ! For pop we will literally just switch front and rear's algorithm, they are literally contemporary to each other
    */

    // Remove from front: Read value, then shift front right
    public void pop_front() {
        if (isEmpty()) {
            System.out.println("UNDERFLOW: Deque is empty");
            return;
        }
        System.out.println("Popped front: " + deque[front]);
        deque[front] = null;
        front = (front + 1) % capacity; // this directly remove the value
        currentSize--;
    }

    // Remove from back: Shift rear left, then read value
    public void pop_back() {
        if (isEmpty()) {
            System.out.println("UNDERFLOW: Deque is empty");
            return;
        }
        rear = (rear - 1 + capacity) % capacity;
        System.out.println("Popped back: " + deque[rear]);
        deque[rear] = null;
        currentSize--;
    }

    // Logically print array items from front to rear indexes
    public void display() {
        if (isEmpty()) {
            System.out.println("Deque status: Empty");
            return;
        }
        System.out.print("Deque view (Front -> Rear): ");
        // * we are going to use front pointer to print this is due to the fact that with this we will know if by using push_front, which value is in front and thus print in this way
        int currIndex = front;
        for (int i = 0; i < currentSize; i++) {
            System.out.print(deque[currIndex] + " ");
            currIndex = (currIndex + 1) % capacity;
        }
        System.out.println();
    }
}

void main() {
        Deque deque = new Deque(5);

        deque.push_back(1);
        deque.push_back(2);
        deque.push_back(3);
        deque.push_back(4);
        deque.push_back(5);
        deque.push_back(6); 
        deque.display();

        deque.push_front(0); 
        deque.display();

        deque.pop_front();
        deque.display();

        deque.pop_back();
        deque.pop_back();
        
        deque.push_front(5);
        deque.display();
}
