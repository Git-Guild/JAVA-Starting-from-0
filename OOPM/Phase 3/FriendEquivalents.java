//TODO: `friend` in C++ vs Java's equivalents (theory in this folder's README.md)

//! Java has NO friend keyword. The three tools that replace it:
// 1) package-private members (same package = trusted circle)
// 2) nested classes (they get access to the outer class's private members for free)
// 3) getters with validation (the "proper" Java way)

class EquilateralTriangle {
    private float side;
    private float perimeter;
    private float area;

    // C++ used friend functions to read these privates; Java exposes small accessors
    void setSide(float side) {
        this.side = side;
        this.area = (float) (Math.sqrt(3.0) * side * side / 4.0);   // Math.sqrt = <cmath>
        this.perimeter = side * 3;
    }

    float getArea()      { return area; }
    float getPerimeter() { return perimeter; }
    float getSide()      { return side; }
}

// same-package helper: package-private classes can trust each other (no `friend` needed)
class Homework {
    void printResults(EquilateralTriangle et) {
        System.out.println("Perimeter: " + et.getPerimeter());
        System.out.println("Area: " + et.getArea());
    }
}

class OuterBox {
    private int secret = 42;   // private to OuterBox...

    class InnerBox {           // ...but a nested class lives inside, so it can read it
        void reveal() {
            // in C++ this needs `friend class InnerBox;` — in Java: nothing to write
            System.out.println("[INNER CLASS] The outer private secret is: " + secret);
        }
    }

    // static nested class: no enclosing object needed
    static class StaticHelper {
        static void hello() {
            System.out.println("[STATIC NESTED] no OuterBox object required");
        }
    }
}

public class FriendEquivalents {
    public static void main(String[] args) {

        EquilateralTriangle et = new EquilateralTriangle();
        et.setSide(3.0f);

        Homework hw = new Homework();
        hw.printResults(et);
        System.out.println();

        OuterBox box = new OuterBox();
        OuterBox.InnerBox inner = box.new InnerBox();   // inner class needs an outer object
        inner.reveal();
        System.out.println();

        OuterBox.StaticHelper.hello();
        System.out.println();

        // C++ friend -> Java cheat sheet:
        // friend function -> a method of the class (getters)
        // friend class    -> nested class, or same-package package-private access
        // one-off grants  -> no syntax exists: refactor to getters instead
    }
}
