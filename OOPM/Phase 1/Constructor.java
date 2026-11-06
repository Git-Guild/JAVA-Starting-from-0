//TODO: Constructors in JAVA (theory in this folder's README.md)

// Rules (same as C++): same name as class, no return type, and writing ANY
// constructor makes the compiler stop generating the default one.
//! Java has no destructor — the garbage collector cleans up instead.

class Employee {

    String Name;
    String Company;
    int Age;

    // constructors can be overloaded, like C++
    Employee() {                     // no-argument constructor
        Name = "Default Name";
        Company = "Default Company";
        Age = 0;
        System.out.println("[0-arg ctor] called");
    }

    Employee(String name, String company) {   // parameterised constructor
        Name = name;
        Company = company;
        Age = 25;
        System.out.println("[2-arg ctor] called");
    }

    Employee(String Name, String Company, int Age) {
        this.Name = Name;   // this.field = this object's field (C++ used this->, Java uses this.)
        this.Company = Company;
        this.Age = Age;
        System.out.println("[3-arg ctor] called");
    }

    // no ~Employee() in Java: cleanup of memory is the GC's job,
    // file/socket cleanup goes in finally or try-with-resources

    void Introduce() {
        System.out.println("Name:" + Name);
        System.out.println("Company:" + Company);
        System.out.println("Age:" + Age);
    }
}

public class Constructor {
    public static void main(String[] args) {

        // new ClassName(args) picks WHICH constructor at compile time, like C++
        Employee emp1 = new Employee("Yuvraj", "Amazon", 19);
        emp1.Introduce();
        System.out.println();

        Employee emp2 = new Employee("Raj", "Google");
        emp2.Introduce();
        System.out.println();

        Employee emp3 = new Employee();
        emp3.Introduce();
        System.out.println();

        // DEFAULT CONSTRUCTOR rule: if you write no constructor at all, javac silently
        // inserts Employee() { super(); }. The moment you write one yourself, it stops.
        // Then new Employee() fails to compile until you add it (same rule as C++).

        // At the end of main these objects become unreachable — the GC reclaims them,
        // so unlike C++ no destructor output appears.
        System.out.println("\nmain() ending: objects are now GC-eligible (no dtor output!)");
    }
}
