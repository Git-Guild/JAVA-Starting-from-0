//TODO: STATIC MEMBERS in JAVA (theory: what static is for, in this folder's README.md)

//! static = belongs to the CLASS, not to any object.
// One shared copy for the whole program, initialised when the class loads.
// Java has no globals — static is the closest legal equivalent.

class Employee {
    private String Name;
    private String Company;
    private int Age;

    // one shared copy for ALL Employee objects
    static int employeeCount = 0;   // no out-of-class definition needed (C++ needed Employee::...)

    // static final = constant for the whole program (see FinalKeyword.java)
    static final String COMPANY_POLICY = "Be excellent to each other.";

    // runs ONCE when the class loads, before any object exists
    static {
        System.out.println("[static block] Employee class loaded, employeeCount = " + employeeCount);
    }

    Employee(String name, String company, int age) {
        Name = name;
        Company = company;
        Age = age;
        employeeCount++;   // every constructor bumps the ONE shared counter
    }

    void Introduce() {
        System.out.println("Name: " + Name + " | Company: " + Company + " | Age: " + Age);
    }

    //! static methods can only touch static members — there is no `this` in them
    static void PrintEmployeeCount() {
        System.out.println("[TOTAL EMPLOYEES]: " + employeeCount);
        // System.out.println(Name);   // compile error: instance field in a static method
    }

    static void PrintPolicy() {
        System.out.println("[POLICY]: " + COMPANY_POLICY);
    }
}

public class StaticMembers {
    public static void main(String[] args) {

        // main() itself is static: the JVM calls it BEFORE any object exists,
        // which is also why String[] args must be static

        Employee.PrintEmployeeCount();   // call static members via the CLASS NAME

        Employee emp1 = new Employee("Raj", "Google", 29);
        Employee emp2 = new Employee("Aman", "Microsoft", 32);
        Employee emp3 = new Employee("Priya", "Amazon", 27);

        emp1.Introduce();
        emp2.Introduce();
        emp3.Introduce();
        System.out.println();

        // the counter moved without telling emp2/emp3 — one shared copy
        Employee.PrintEmployeeCount();
        Employee.PrintPolicy();

        System.out.println();
        System.out.println("Count via class:  " + Employee.employeeCount);
        System.out.println("Count via object: " + emp1.employeeCount + " (same number! just a confusing way to write it)");

        // When to use: shared counters/config (static field), utilities that use only
        // their parameters (static method), constants (static final), one-time setup
        // (static block). Never make fields public static — keep them private.
    }
}
