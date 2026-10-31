class Node {
    int data;
    Node next;
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// Return the updated head (Java's stand-in for C++'s pass-by-reference Node* &head — see InsertAtFirst.java)
public class InsertAtPos {
    static Node insertAtPos(Node head, int pos, int val) {
        if (pos < 1) { // pos cannot be 0 or negative
            System.out.println("Position out of bounds!");
            return head;
        }

        if (pos == 1) { // same as insertAtFirst
            Node newNode = new Node(val);
            newNode.next = head;
            return newNode;
        }

        Node cur = head;
        // Traverse to the node just before the target position (pos - 1 steps from head)
        for (int i = 1; i < pos - 1 && cur != null; i++) {
            cur = cur.next;
        }

        // If cur is null, the requested position is past the end + 1
        if (cur == null) {
            System.out.println("Position out of bounds!");
            return head;
        }

        /*
        your list: [10] -> [20] -> [30]
        you want to insert 40 at pos 3
        cur is pointing at 20, cur.next is 30
        */
        Node newNode = new Node(val);
        /*
        left side is variable, right side is value
        so newNode (40) is now pointing at 30
        cur (20) is now pointing at 40
        so 2 values are now pointing at 30
        */
        newNode.next = cur.next; 
        
        // tell cur to point to newNode i.e. 20 -> 40 (cur was 20, previously cur.next was 30 but now cur.next is 40)
        cur.next = newNode;
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

        head = insertAtPos(head, 1, 10);  // list: 10 (value=10 at pos=1)
        head = insertAtPos(head, 2, 20);  // list: 10 -> 20
        head = insertAtPos(head, 3, 30);  // list: 10 -> 20 -> 30
        head = insertAtPos(head, 3, 40);  // list: 10 -> 20 -> 40 -> 30
        head = insertAtPos(head, 5, 50);  // list: 10 -> 20 -> 40 -> 30 -> 50
        head = insertAtPos(head, 7, 99);  // prints "Position out of bounds!"

        System.out.print("Linked List: ");
        printList(head);
    }
}

// The values and positions are hardcoded (manual representation) here —
// you can make it dynamic by taking them from the user (Scanner).

/*
Output:
Position out of bounds!
Linked List: 10 -> 20 -> 40 -> 30 -> 50 -> NULL
*/
