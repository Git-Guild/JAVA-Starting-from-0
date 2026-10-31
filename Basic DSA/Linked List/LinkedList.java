//TODO: Learning the Practical approach of linked lists
// please refer to BasicLinkedList.java to get a basic idea of what we are advancing on
// we will also not be using this in our explanations but it is good to know and implement it in projects

class Node {
    int data;
    Node next;
    Node(int data) { 
        this.data = data;
        this.next = null; 
    }
}
/*
We will make a separate class and add methods in it so we can directly use add and print in main without manually typing out logic in main
*/
public class LinkedList {
    Node head; //* we initialise node head in the class 
    LinkedList() {
        this.head = null; //constructor while making the LinkedList empty
    }
    void add(int data) {
        /*
        ! Logic:
        * if head is null then we add the node to the head
        * else we add the node to the end of the list while the previous one's next point to this new node
        * we use a reference temp to traverse the list
        */
        Node newNode = new Node(data);
        if (this.head == null) {
            this.head = newNode;
            return;
        }
        Node temp = this.head;
        while (temp.next != null) {
            temp = temp.next; // traverse until the last node
        }
        temp.next = newNode; // add the new node to the end of the list
    }
    void print() {
        Node temp = this.head;
        while (temp != null) {
            System.out.print(temp.data + "->");
            temp = temp.next;
        }
        System.out.println("Null");
    }

    public static void main(String[] args) {

        LinkedList myLinkedList = new LinkedList(); //make an object of LinkedList class
        myLinkedList.add(1);
        myLinkedList.add(2);
        myLinkedList.add(3);
        myLinkedList.add(4);
        myLinkedList.add(5);
        myLinkedList.print();
        
        LinkedList myLinkedList1 = new LinkedList();
        myLinkedList1.add(2);
        myLinkedList1.print();
    }
}

// The values added below are hardcoded (manual representation) —
// you can make it dynamic by taking the values from the user (Scanner).

/*
Output:
1->2->3->4->5->Null
2->Null
*/
