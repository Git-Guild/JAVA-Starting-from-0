//TODO: Encapsulation in JAVA (theory + access-level table in this folder's README.md)

//* Encapsulation = keep fields private and expose them through getters/setters,
// so validation and internal details stay inside the class.

class Employee {
    private String Name;      // private: invisible outside this class
    private String Company;
    private int Age;

    // setters (write access)
    void setName(String name) {
        Name = name;
    }

    void setCompany(String company) {
        Company = company;
    }

    void setAge(int age) {
        if (age > 18) {
            Age = age;   // validation lives inside the setter
        } else {
            System.out.println("[REJECTED] Age must be above 18 — field keeps its old value.");
            // known gap: if no age was ever set, Age stays 0 — fix it with a constructor
        }
    }

    // getters (read access)
    String getName() {
        return Name;
    }

    String getCompany() {
        return Company;
    }

    int getAge() {
        return Age;
    }

    void Introduce() {
        System.out.println("Name:" + Name);
        System.out.println("Company:" + Company);
        System.out.println("Age:" + Age);
    }
}

public class Encapsulation {
    public static void main(String[] args) {

        Employee emp1 = new Employee();

        // emp1.Name = "Raj";   // would NOT compile: Name is private (try it!)
        emp1.setName("Raj");          // outside code goes through the setter instead
        emp1.setCompany("Microsoft");
        emp1.setAge(30);

        emp1.Introduce();

        System.out.println(emp1.getName() + " is " + emp1.getAge()
                + " years old and works at " + emp1.getCompany());

        System.out.println();

        // prove the validation works: 15 is rejected, the field is unchanged
        emp1.setAge(15);
        System.out.println("Age after trying to set 15: " + emp1.getAge());
    }
}
