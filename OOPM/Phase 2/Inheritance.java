//TODO: Inheritance in JAVA (theory in this folder's README.md)

//! Syntax: class Child extends Parent { ... }
//* Always public inheritance; Java has no : private/: protected like C++.
//! One class parent only (Java avoids C++'s diamond problem) — extra behaviour
// comes from implementing interfaces instead.

// pure contract first
interface AskforPromotion {
    void CheckPromotion();
}

class Employee implements AskforPromotion {
    private String Name;   // private: even subclasses go through getters
    private String Company;
    private int Age;

    Employee(String name, String company, int age) {
        Name = name;
        Company = company;
        Age = age;
    }

    // the only way children and outside code can read the fields
    String getName()    { return Name; }
    String getCompany() { return Company; }
    int getAge()        { return Age; }

    void Introduce() {
        System.out.println("Name:" + Name);
        System.out.println("Company:" + Company);
        System.out.println("Age:" + Age);
    }

    @Override
    public void CheckPromotion() {
        if (Age >= 30)
            System.out.println("[PROMOTION]: " + Name + " is eligible for promotion!");
        else
            System.out.println("[PROMOTION]: " + Name + " is not eligible yet.");
    }

    // no destructor: the GC reclaims memory silently (C++ printed "Derived Destructor called!")
}

// 1) single inheritance
class Dev extends Employee {
    String favLanguage;

    Dev(String name, String company, int age, String favLanguage) {
        super(name, company, age);   // super(...) passes arguments up to Employee's constructor
        this.favLanguage = favLanguage;
    }

    void FixBug() {
        System.out.println(getName() + " is fixing bug...");   // inherited getter
    }
}

// 2) hierarchical inheritance: Teacher extends Employee too (sibling of Dev)
class Teacher extends Employee {
    String Subject;

    Teacher(String name, String company, int age, String subject) {
        super(name, company, age);
        Subject = subject;
    }

    void Teach() {
        System.out.println(getName() + " is teaching " + Subject);
    }
}

// 3) multilevel inheritance: LeadDev -> Dev -> Employee (a chain)
class LeadDev extends Dev {
    int teamSize;

    LeadDev(String name, String company, int age, String favLanguage, int teamSize) {
        super(name, company, age, favLanguage);   // chain: LeadDev -> Dev -> Employee
        this.teamSize = teamSize;
    }

    void ManageTeam() {
        System.out.println(getName() + " is managing a team of " + teamSize + " developers.");
    }
}

// 4) "multiple inheritance" via interfaces:
// C++ did: class TechLead : public Employee, public Speaker
// Java does: one class parent + as many interfaces as you like
interface Speaker {
    void Speak();   // implicitly public abstract
}

class TechLead extends Employee implements Speaker {
    TechLead(String name, String company, int age) {
        super(name, company, age);
    }

    @Override
    public void Speak() {
        System.out.println("[SPEAKER]: Speaking about System Design");
    }

    void GuideProject() {
        System.out.println(getName() + " is guiding the project architecture.");
    }
}

// 5) hybrid = multilevel chain + interfaces combined
interface Mentor {
    void MentorSomeone();
}

class HybridLead extends LeadDev implements Mentor {
    HybridLead(String name, String company, int age, String lang, int teamSize) {
        super(name, company, age, lang, teamSize);
    }

    @Override
    public void MentorSomeone() {
        System.out.println(getName() + " is mentoring juniors (multilevel + interface = hybrid).");
    }
}

public class Inheritance {
    public static void main(String[] args) {

        // single
        Dev d = new Dev("Raj", "Google", 29, "Java");
        d.FixBug();
        d.Introduce();
        d.CheckPromotion();
        System.out.println();

        // multilevel
        LeadDev ld = new LeadDev("Aman", "Microsoft", 32, "Java", 5);
        ld.FixBug();
        ld.ManageTeam();
        ld.CheckPromotion();
        System.out.println();

        // interface-based "multiple"
        TechLead tl = new TechLead("Priya", "Amazon", 35);
        tl.Introduce();
        tl.Speak();
        tl.GuideProject();
        tl.CheckPromotion();
        System.out.println();

        HybridLead hl = new HybridLead("Sriya", "Google", 34, "Java", 8);
        hl.ManageTeam();
        hl.MentorSomeone();
        System.out.println();

        // no `new`/`delete` games: heap objects are GC-managed
        // Task: base-class reference on a derived object, watch dynamic dispatch
        AskforPromotion ref = d;
        ref.CheckPromotion();
    }
}
