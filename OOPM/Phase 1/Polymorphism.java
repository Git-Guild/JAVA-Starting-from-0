//TODO: Polymorphism in JAVA (theory in this folder's README.md)

//* Overloading  = same name, different parameters, chosen at COMPILE time.
//* Overriding   = derived class redefines the base method, chosen at RUN time.
//* Java: instance methods are virtual by default (no `virtual` keyword like C++).
//! @Override is only a compiler check — it does not change behaviour.

class Employee {
    protected String Name;   // protected: subclasses can see it, the outside world cannot
    protected String Company;
    protected int Age;

    Employee(String name, String company, int age) {
        Name = name;
        Company = company;
        Age = age;
    }

    // virtual by default: subclasses can override this
    void Work() {
        System.out.println(Name + " is checking emails and performing general tasks.");
    }
    // no virtual destructor needed — Java has no destructors
}

class Dev extends Employee {   // extends = C++'s : public Employee
    String favLanguage;

    Dev(String name, String company, int age, String language) {
        super(name, company, age);   //* super(...) calls the base constructor, must be first line
        favLanguage = language;
    }

    // overriding: same name + same parameters as the base method
    @Override
    void Work() {
        System.out.println(Name + " is writing code in " + favLanguage + " and fixing bugs!");
    }
}

public class Polymorphism {
    public static void main(String[] args) {

        Dev d = new Dev("Raj", "Google", 29, "Java");

        // C++ needed a base-class pointer (Employee* e = &d) for this trick.
        // Java only needs a base-class reference — no pointers at all.
        Employee e = d;   // upcasting: derived -> base (implicit and always safe)

        System.out.println("Calling Work() via Base Class Reference:");
        e.Work();   // runs Dev's version: the JVM dispatches on the real object type

        System.out.println("\nCalling Work() via Object Directly:");
        d.Work();

        // This is done in cases where we don't know what the object type will be at runtime.
        //! In real world we will use this when we have a base class and it is derived to multiple classes. Using something like e = d, will allow us to make the program more extensible.
        // Like imagine adding a bunch of people with different roles in a single team, you can just use Employee e and loop with adding them with instances of the derived classes
        // this removes manual initialization and makes the program more flexible
        System.out.println("\nCompile-time vs Run-time: same reference, different answers:");
        OverloadNote.show(e);   // overload picked by the REFERENCE type at compile time
        OverloadNote.show(d);
        // Both objects are actually Dev — only overriding (Work) looks at the real object.
    }
}

// overloading is chosen by the reference type at COMPILE time;
// overriding is chosen by the object type at RUN time
class OverloadNote {
    static void show(Employee emp) { System.out.println("I see an Employee reference"); }
    static void show(Dev dev)       { System.out.println("I see a Dev reference"); }
}
