class Node { 
    int data; 
    Node next; 
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// Return the head so main can reassign it — Java's stand-in for C++'s pass by reference (Node* &head)
public class delAtPos { 
    static Node delAtPos(Node head, int pos) {
        // Edge Case: Invalid position or empty list
        if (head == null || pos <= 0) {
            return head;
        } 
        
        // Case 1: Delete the head node (position 1)
        if (pos == 1) {
            Node temp = head.next;
            // C++ would `delete` the old head here — the garbage collector handles it in Java
            return temp;
        }

        // Case 2: Traverse to the node just BEFORE the one we want to delete
        Node temp = head;
        for (int i = 1; i < pos - 1; i++) {
            // If the position is out of bounds, exit safely
            if (temp == null) {
                return head;
            }
            temp = temp.next;
        }
        
        // Safety check: Make sure temp and the node to delete (temp.next) actually exist
        if (temp == null || temp.next == null) {
            return head;
        }
        
        // Safely untie and delete the node
        temp.next = temp.next.next;
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(1); 
        Node second = new Node(2); 
        Node third = new Node(3); 
        
        head.next = second; 
        second.next = third; 
        third.next = null; 
        
        // Deleting position 2 (the node with value 2)
        head = delAtPos(head, 2); 
        
        // Print the remaining list
        Node temp = head; 
        while (temp != null) { 
            System.out.print(temp.data + "->"); 
            temp = temp.next; 
        } 
        System.out.println("Null"); 
    }
}

// The nodes and the position are hardcoded (manual representation) here —
// you can make it dynamic by taking them from the user (Scanner).

/*
Output:
1->3->Null
*/
