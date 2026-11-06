//TODO: More About Constructors in JAVA (theory in this folder's README.md)

//! this(...) = delegate to another constructor of the same class (first statement only)
//! super(...) = call the parent constructor (first statement only)
// Java never copies objects for you — write a copy constructor if you want one.

class Employee {
    String Name;
    String Company;
    int Age;

    Employee() {
        this("Unknown", "Unemployed", 0);      // chain -> the 3-arg ctor
        System.out.println("[0-arg ctor] finished (after delegating)");
    }

    Employee(String name, String company) {
        this(name, company, 25);               // chain -> the 3-arg ctor
        System.out.println("[2-arg ctor] finished");
    }

    Employee(String name, String company, int age) {
        this.Name = name;
        this.Company = company;
        this.Age = age;
        System.out.println("[3-arg ctor] ran the real work");
    }

    void Introduce() {
        System.out.println("Name:" + Name + " | Company:" + Company + " | Age:" + Age);
    }
}

// copy constructor: Java's `=` only copies the reference, so deep copies are written by hand
class Account {
    String owner;
    double balance;

    Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    Account(Account other) {
        this.owner = other.owner;
        this.balance = other.balance;
    }

    @Override
    public String toString() {
        return owner + " has $" + balance;
    }
}

// private constructor + static factory: outside code cannot use `new`, only connect()
class DatabaseConnection {
    private String url;

    private DatabaseConnection(String url) {
        this.url = url;
    }

    // factories can return cached instances or meaningful nulls — `new` cannot
    static DatabaseConnection connect(String url) {
        System.out.println("[FACTORY] opening connection to " + url);
        return new DatabaseConnection(url);
    }

    String getUrl() { return url; }
}

public class MoreAboutConstructors {
    public static void main(String[] args) {

        System.out.println("creating via 0-arg ctor:");
        Employee a = new Employee();
        a.Introduce();

        System.out.println("\ncreating via 2-arg ctor:");
        Employee b = new Employee("Raj", "Google");
        b.Introduce();
        System.out.println();

        // copy constructor vs aliasing (the classic Java trap)
        Account original = new Account("Raj", 100);
        Account copy = new Account(original);   // separate object — safe
        Account alias = original;               // NO copy: two names, ONE object
        alias.balance = 999;                    // changes 'original' too

        System.out.println("original (after alias changed it): " + original);
        System.out.println("copy     (safe, separate object) : " + copy);
        System.out.println("alias == original ? " + (alias == original));   // true: same object
        System.out.println();
        System.out.println("C++ would have copied the object on `Account copy = original;`");
        System.out.println("Java ALWAYS copies only the reference. Want a copy? Write a copy ctor.");
        System.out.println();

        // no move constructor in Java: no ownership, no delete, nothing to steal
        System.out.println("Why no move semantics?:");
        System.out.println("C++ moved objects to avoid expensive copies (rvalue refs &&).");
        System.out.println("Java copies a REFERENCE in O(1), so moving gains nothing.");
        System.out.println();

        // initialization order
        System.out.println("Initialization order for a new Employee:");
        System.out.println("1. static fields/blocks (once, when class loads)");
        System.out.println("2. instance field initialisers run (top to bottom)");
        System.out.println("3. constructor body runs (after the super()/this() call)");
        OrderProbe probe = new OrderProbe();
        System.out.println();

        // private constructor + factory
        DatabaseConnection db = DatabaseConnection.connect("jdbc:mysql://localhost:3306/shop");
        // new DatabaseConnection("x");   // compile error: constructor is private
        System.out.println("Created via factory: " + db.getUrl());
    }
}

class OrderProbe {
    int x = print("field initialiser x");   // runs BEFORE the ctor body

    OrderProbe() {
        System.out.println("3. constructor body runs (x is now " + x + ")");
    }

    static int print(String msg) {
        System.out.println("2. " + msg);
        return 1;
    }
}
