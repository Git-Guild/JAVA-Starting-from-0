//TODO: Operator Overloading in JAVA — it does not exist (theory in this folder's README.md)

//* The only operator Java gives meaning to beyond the primitives: `+` on String
//! (concatenation). You can never overload any operator yourself.
// Instead: named methods — add(), equals(), compareTo(), toString().

class Car implements Comparable<Car> {   // Comparable = how to order objects
    String brand;
    int speed;
    int distance;

    Car(String brand, int speed, int distance) {
        this.brand = brand;
        this.speed = speed;
        this.distance = distance;
    }

    // stands in for C++'s operator+: returns a NEW object
    Car add(Car other) {
        return new Car(
                this.brand + " & " + other.brand,   // String + is the one legal case
                this.speed + other.speed,
                this.distance + other.distance
        );
    }

    // stands in for C++'s operator==: value comparison (== itself compares references)
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Car)) return false;
        Car c = (Car) other;
        return this.brand.equals(c.brand)   // Strings need .equals(), not ==
                && this.speed == c.speed
                && this.distance == c.distance;
    }

    // stands in for C++'s operator<: negative/zero/positive ordering
    @Override
    public int compareTo(Car other) {
        return Integer.compare(this.speed, other.speed);   // ascending by speed
    }

    // stands in for C++'s operator<<: println prints this automatically
    @Override
    public String toString() {
        return "Car: " + brand + " | Speed: " + speed + " | Distance: " + distance;
    }
}

public class OperatorOverloading {
    public static void main(String[] args) {

        Car car1 = new Car("Audi", 100, 1000);
        Car car2 = new Car("BMW", 80, 2000);
        Car car3 = new Car("Mercedes", 120, 3000);

        // C++: cout << car1 + car2 + car3;
        Car combined = car1.add(car2).add(car3);
        System.out.println(combined);
        System.out.println();

        System.out.println("car1.equals(car2)? " + car1.equals(car2));   // false
        System.out.println("car1.equals(car1)? " + car1.equals(car1));   // true
        System.out.println("car1 == car2       ? " + (car1 == car2));    // false — reference compare
        System.out.println();

        // sorting works only because Car implements Comparable (C++ needed operator<)
        Car[] garage = { car3, car1, car2 };
        java.util.Arrays.sort(garage);
        for (Car c : garage) System.out.println("sorted -> " + c);
        System.out.println();

        // no unary operators at all: just write normal code
        car1.speed += 10;
        System.out.println(car1);
    }
}
