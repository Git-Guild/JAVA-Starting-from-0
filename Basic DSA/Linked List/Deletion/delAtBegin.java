class Node { 
    int data; 
    Node next; 
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// Return the head so main can reassign it — Java's stand-in for C++'s pass by reference (Node* &head)
public class delAtBegin { 
    static Node delAtBegin(Node head) { 
        if (head == null) {
            return null;
        }
        head = head.next; // Move head to the next node
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(1); 
        Node second = new Node(2); 
        Node third = new Node(3); 
        
        head.next = second; 
        second.next = third; 
        third.next = null; 
        
        head = delAtBegin(head); 
        
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
2->3->Null
*/
