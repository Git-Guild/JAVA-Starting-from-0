//TODO: Insert a new node at the end of the list

// Node, printList and the approach are identical to InsertAtFirst.java —
// read that file first; only the NEW logic is explained in detail here.
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class InsertAtLast {
    static Node insertAtLast(Node head, int data) {
        Node newNode = new Node(data);
        
        //! Empty-list check: if head is null there is no last node to walk to,
        // so the new node simply becomes the head.
        //* Skipping this check would make the while loop below dereference null
        // and crash with a NullPointerException.
        if (head == null) {
            return newNode;
        }

        // Walk until temp sits ON the last node (the one whose next is null).
        // Note: the condition checks temp.next, NOT temp — we stop one node early.
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }

        // The old last node now links to the new node; no other node changes
        temp.next = newNode;
        return head;
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
        Node head = null;

        // Inserting at the last position KEEPS insertion order (1..5, not reversed)
        head = insertAtLast(head, 1);
        head = insertAtLast(head, 2);
        head = insertAtLast(head, 3);
        head = insertAtLast(head, 4);
        head = insertAtLast(head, 5);

        System.out.print("Linked List: ");
        printList(head);
    }
}

// The values passed to insertAtLast are hardcoded (manual representation) —
// you can make it dynamic by taking them from the user (Scanner).

/*
Output:
Linked List: 1 -> 2 -> 3 -> 4 -> 5 -> NULL
*/
