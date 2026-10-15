//TODO: Learning the basics of linked list

//* A node holds two things: its own value (data) and a link to the next node (next)
class Node { // struct is a C++ thing, not Java — Java only has class, and a plain class does the exact same job here
    int data;
    Node next;
    Node(int data) {
        this.data = data; // start as only one node in the list
        this.next = null; // null is Java's equivalent of C++'s nullptr/NULL — it simply means "points at nothing"
    }
}

public class BasicLinkedList {
    public static void main(String[] args) {
        //! Manual way to link and add nodes
        //* we can use another class to make this easy for us but I suggest using this in exams and interviews to save time
        // for a better practical approach check out LinkedList.java

        Node head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);

        head.next = second; // we link the head to the second and so on to link the individual nodes
        second.next = third;
        third.next = null;

        Node temp = head; 
        while (temp != null) {
            System.out.print(temp.data + "->");
            temp = temp.next; // hop to the next node
        }
        System.out.println("Null"); // printed once, after the loop
    }
}

// The nodes are created and linked manually (hardcoded values) in this file —
// you can make it dynamic by taking the values from the user (Scanner).

/*
Output:
1->2->3->Null
*/
