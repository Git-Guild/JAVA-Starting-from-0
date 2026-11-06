//TODO: Classes and Objects in JAVA (theory in this folder's README.md)

// Java has no struct: use class, record or enum (Phase 3\EnumsAndRecords.java)

// Class = blueprint, Object = an instance of that blueprint.
// Every class you write secretly extends java.lang.Object.

class Employee {
    // no access keyword = package-private (Java's default for members)
    String Name;
    String Company;
    int Age;

    void Introduce() {
        System.out.println("Name:" + Name);
        System.out.println("Company:" + Company);
        System.out.println("Age:" + Age);
    }

    //* function overloading: same name, different parameters (resolved at compile time)
    void Introduce(String name, String company, int age) {
        Name = name;
        Company = company;
        Age = age;
        System.out.println("Name:" + Name);
        System.out.println("Company:" + Company);
        System.out.println("Age:" + Age);
    }

    // parameter order can change too — picks the other version
    void Introduce(int age, String company, String name) {
        Name = name;
        Company = company;
        Age = age;
        System.out.println("Sequence changed parameters:");
        System.out.println("Name:" + Name);
        System.out.println("Company:" + Company);
        System.out.println("Age:" + Age);
    }
}

// A second top-level class in the same file: legal because it is NOT public.
// javac compiles it into its own file: Helper.class
class Helper {
    String role = "Assistant";

    void help() {
        System.out.println("[HELPER] I am a second top-level class in the same file, role: " + role);
    }
}

// main must live inside a class, and its signature is always exactly this:
// public static void main(String[] args)
public class ClassesandObjects {
    public static void main(String[] args) {
        //! Employee emp4();   // would NOT compile: due to Java's static typing, must use NEW
        Employee emp1 = new Employee();   //* new + () = constructor call, object goes on the heap
        emp1.Name = "Yuvraj";             // dot operator — Java has no ->
        emp1.Age = 19;
        emp1.Company = "Amazon";
        emp1.Introduce();                 // no-parameter version chosen at compile time

        System.out.println();

        Employee emp2 = new Employee();
        emp2.Introduce("Rahul", "Google", 39);

        System.out.println();

        Employee emp3 = new Employee();
        emp3.Introduce(29, "Changed", "Sequence");

        System.out.println();

        Helper h = new Helper();          // same file, so package-private access works
        h.help();

        System.out.println();

        // toString() and instanceof come from java.lang.Object
        System.out.println("emp1's default toString(): " + emp1);   // ClassName@hashcode
        System.out.println("emp1 is an Object? " + (emp1 instanceof Object));   // true
    }
}
