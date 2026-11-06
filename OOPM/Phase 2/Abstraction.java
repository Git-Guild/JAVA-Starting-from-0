//TODO: Abstraction in JAVA (theory in this folder's README.md)

//! Abstraction = show WHAT a thing does, hide HOW it does it.
// Two tools: abstract class (state + shared code) and interface (pure contract).

//* interface: all methods are abstract by default, no constructors
interface AskforPromotion {
    void CheckPromotion();   // implicitly public abstract, MUST BE IMPLEMENTED

    // default method: a body inside an interface (Java 8+) — extends an interface
    // without breaking the classes that already implement it
    default void Introduce(String name) {
        System.out.println("[DEFAULT METHOD] Hi, I am " + name + " asking for a promotion.");
    }

    // interface fields are constants: implicitly public static final
    int MIN_AGE = 30;
}

// abstract class: can hold real fields AND a constructor, may stay partially unfinished
abstract class Employee implements AskforPromotion {   // implements = class adopts an interface
    String Name;
    String Company;
    int Age;

    Employee(String name, String company, int age) {
        Name = name;      // parameter names differ from fields to avoid shadowing
        Company = company;
        Age = age;
    }

    void Introduce() {
        System.out.println("Name: " + Name + " | Company: " + Company + " | Age: " + Age);
    }

    // concrete method shared by every subclass (abstract classes can use their fields here)
    protected boolean isSenior() {
        return Age >= MIN_AGE;
    }
}

// concrete class: implements the remaining contract, so it can be instantiated
class Dev extends Employee {
    String favLanguage;

    Dev(String name, String company, int age, String language) {
        super(name, company, age);   // must be the first statement of the constructor
        favLanguage = language;
    }

    // interface methods are public, so the override must be public too
    @Override
    public void CheckPromotion() {
        if (Age >= AskforPromotion.MIN_AGE)
            System.out.println("[PROMOTION]: " + Name + " is eligible for promotion!");
        else
            System.out.println("[PROMOTION]: " + Name + " is not eligible yet.");
    }
}

public class Abstraction {
    public static void main(String[] args) {

        Dev emp1 = new Dev("Raj", "Microsoft", 30, "Java");
        emp1.Introduce();

        // C++ used pointers (&, ->) and references here; Java has only references,
        // and `new` is mandatory for objects (no stack-allocated objects)

        Employee e1 = emp1;              // reference typed to the abstract class
        e1.CheckPromotion();             // dynamic dispatch -> Dev's version runs
        System.out.println();

        AskforPromotion p = emp1;        // reference typed to the interface (most flexible)
        p.CheckPromotion();
        p.Introduce("Raj");              // default method called through the interface
        System.out.println();

        // AskforPromotion x = new AskforPromotion();   // error: interface, no instantiation
        // Employee y = new Employee("a","b",1);        // error: abstract class, same rule
        System.out.println("Interfaces and abstract classes can never be instantiated — contract only!");
    }
}

// STACK vs HEAP: locals (primitives + references) live on the stack and vanish when the
// method returns; every `new` object lives on the heap until the GC reclaims it.
