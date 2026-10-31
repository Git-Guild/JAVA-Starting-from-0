class Node { 
    int data; 
    Node next; 
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// Return the head so main can reassign it — Java's stand-in for C++'s pass by reference (Node* &head)
public class delAtLast { 
    static Node delAtLast(Node head) { 
        // Case 1: List is completely empty
        if (head == null) {
            return null;
        } 
        
        // Case 2: List has only one node
        if (head.next == null) {
            // C++ would `delete head` here — the garbage collector handles it in Java
            return null;
        }
        
        // Case 3: List has 2 or more nodes
        Node temp = head; 
        // Stop at the second-to-last node
        while (temp.next.next != null) { 
            temp = temp.next; 
        } 
        
        // temp.next is the actual last node
        temp.next = null; // Disconnect the second-to-last node
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(1); 
        Node second = new Node(2); 
        Node third = new Node(3); 
        
        head.next = second; 
        second.next = third; 
        third.next = null; 
        
        head = delAtLast(head); 
        
        Node temp = head; 
        while (temp != null) { 
            System.out.print(temp.data + "->"); 
            temp = temp.next; 
        } 
        System.out.println("Null"); 
    }
}

// The nodes are linked manually (hardcoded values) in this file —
// you can make it dynamic by taking the values from the user (Scanner).

/*
Output:
1->2->Null
*/
