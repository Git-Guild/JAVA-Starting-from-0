//TODO: Insert a new node at the front of the list

class Node {
    int data;
    Node next;

    // Constructor: members are set the moment the object is created.
    // `next = null` guarantees a fresh node never points at garbage memory.
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

/* 
In C++ this would take Node *&head — a REFERENCE to the head pointer, not a copy of it.
Java has no way to reassign the caller's reference from inside a method,
so we RETURN the updated head and store it back in main().
If we only modified the parameter, main()'s head would NOT change.
*/
public class InsertAtFirst {
    static Node insertAtFirst(Node head, int data) { // our data type is static Node because we do not wanna change anything while importing from Node class
        Node newNode = new Node(data); // allocate a node on the heap (new returns a reference)
        newNode.next = head;           // new node links to the old first node
        return newNode;                // head now points at the new node (main reassigns it)
    }

    static void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("NULL");
    }

    public static void main(String[] args) {
        Node head = null; // start with an empty list

        // Each call puts the new number in FRONT, so the list comes out reversed
        head = insertAtFirst(head, 1); // in java we will call via head = insertAtFirst(head, 1);
        head = insertAtFirst(head, 2);
        head = insertAtFirst(head, 3);
        head = insertAtFirst(head, 4);
        head = insertAtFirst(head, 5);

        System.out.print("Linked List: ");
        printList(head);
    }
}

// The values passed to insertAtFirst are hardcoded (manual representation) —
// you can make it dynamic by taking them from the user (Scanner).

/*
Output:
Linked List: 5 -> 4 -> 3 -> 2 -> 1 -> NULL
*/
