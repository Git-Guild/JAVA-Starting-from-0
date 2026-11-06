//TODO: ENUMS and RECORDS — Java's answers to C++ `struct` (theory in this folder's README.md)

//! enum   = a full class for a fixed set of constants (type-safe, can have
//          fields/methods/implements interfaces; only the declared constants exist)
//! record = the struct Java was missing: private final fields, constructor,
//          getters, equals/hashCode/toString generated for you

enum Season {
    WINTER, SPRING, SUMMER, FALL;   // the only Season objects that will ever exist
}

enum Role implements HasLabel {   // enums can implement interfaces like any class
    DEV("Writes the code"),
    TESTER("Breaks the code"),
    MANAGER("Talks about the code");

    private final String description;

    Role(String description) {   // runs once per constant
        this.description = description;
    }

    String getDescription() { return description; }

    @Override
    public void showLabel() {
        System.out.println(name() + ": " + description);   // name() = the constant's name
    }
}

interface HasLabel {
    void showLabel();
}

// one line = class + fields + ctor + getters + equals + hashCode + toString
record Point(int x, int y) {
    // compact constructor: validation runs automatically in the canonical ctor
    Point {
        if (x < 0 || y < 0) throw new IllegalArgumentException("Points can't be negative: " + x + "," + y);
    }

    double distanceFromOrigin() {   // records can have methods, but no extra instance fields
        return Math.sqrt(x * x + y * y);
    }
}

record Student(String name, int roll) {}   // empty body is legal — everything is generated

public class EnumsAndRecords {
    public static void main(String[] args) {

        Season s = Season.SUMMER;
        System.out.println("Season: " + s);                        // toString built in
        System.out.println("ordinal (position): " + s.ordinal());  // SUMMER is index 2
        System.out.println("all seasons: " + java.util.Arrays.toString(Season.values()));

        // Season s2 = 2;            // compile error — C++ enums ALLOWED this
        // Season s3 = Season.RAINY; // compile error: not a declared constant

        // switch on enums, no fall-through (Java 21+ arrow form)
        switch (s) {
            case WINTER -> System.out.println("Wear a jacket");
            case SUMMER -> System.out.println("Drink water");
            default     -> System.out.println("Enjoy the day");
        }
        System.out.println();

        // enum with behaviour: each constant is a tiny pre-made object
        for (Role r : Role.values()) {
            r.showLabel();
        }
        System.out.println();

        Point p = new Point(3, 4);
        System.out.println("p = " + p);                 // toString free: Point[x=3, y=4]
        System.out.println("x = " + p.x());             // getter is x(), NOT getX()
        System.out.println("distance = " + p.distanceFromOrigin());

        Point q = new Point(3, 4);
        System.out.println("p.equals(q)? " + p.equals(q));   // value compare — a C++ struct can't
        System.out.println("p == q?      " + (p == q));      // false: two different objects
        System.out.println("hashCode equal? " + (p.hashCode() == q.hashCode()));
        System.out.println();

        Student st = new Student("Raj", 17);
        System.out.println(st);   // Student[name=Raj, roll=17]

        // the compact constructor rejects bad data automatically:
        try {
            new Point(-1, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
