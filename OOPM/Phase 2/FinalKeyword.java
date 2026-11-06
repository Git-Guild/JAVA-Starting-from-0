//TODO: `final` in JAVA (theory in this folder's README.md)

//! final VARIABLE -> constant, assigned once
//! final METHOD   -> cannot be overridden (still can be overloaded)
//! final CLASS    -> cannot be extended (String, Integer are final)
//! static final   -> program-wide constant, ALL_CAPS by convention
//* Trap: final freezes the REFERENCE, not the object's fields.

class Employee {
    String Name;
    int Age;

    Employee(String name, int age) {
        Name = name;
        Age = age;
    }

    // subclasses can inherit this but never override it
    final void BeLoyal() {
        System.out.println(Name + " is loyal to the company forever.");
    }

    void Introduce() {
        System.out.println("Name:" + Name + " Age:" + Age);
    }
}

class Dev extends Employee {
    Dev(String name, int age) {
        super(name, age);
    }

    // void BeLoyal() { ... }   //! compile error: cannot override a final method

    @Override
    void Introduce() {   // non-final methods can still be overridden
        System.out.println("[DEV VERSION] " + Name + " codes instead of introducing.");
    }
}

// a final class cannot be extended (like Java's String)
final class ImmutablePoint {
    private final int x;   // final field: set once, in the constructor
    private final int y;

    ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() { return x; }   // no setters — outside code can never change x/y
    int getY() { return y; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class FinalKeyword {
    public static void main(String[] args) {

        // final variable
        final int MAX_TRIES = 3;
        System.out.println("MAX_TRIES = " + MAX_TRIES);
        // MAX_TRIES = 5;   // compile error: cannot assign a value to final variable

        // final reference: the object's fields can still change!
        final Employee e = new Employee("Raj", 29);
        e.Name = "Hacked";                    // legal — only the reference is frozen
        System.out.println("After e.Name = \"Hacked\": " + e.Name);
        // e = new Employee("Amit", 30);       // compile error: reference itself is final

        // final method (no override) vs overridable method
        Dev d = new Dev("Raj", 29);
        d.BeLoyal();
        d.Introduce();   // Dev's version runs

        // final class
        ImmutablePoint p = new ImmutablePoint(3, 4);
        System.out.println("Immutable point: " + p);
        // class Bad extends ImmutablePoint { }   // compile error: final class

        // static final = the classic constant
        System.out.println("Policy: " + CompanyPolicy.POLICY);
        // CompanyPolicy.POLICY = "new policy";   // compile error

        // final vs finally vs finalize():
        // final    -> this file (constants / no-override / no-extend)
        // finally  -> a block after try/catch that always runs (Exception phase)
        // finalize -> Object's GC hook, deprecated — never use
    }
}

class CompanyPolicy {
    static final String POLICY = "Ship early, ship safe.";
}
