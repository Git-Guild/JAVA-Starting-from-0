//& First, two words you need.
//!   A class is a blueprint, like the plan for a house.
//!   An object is one real thing built from that plan, like one house.
// You build an object with the word `new`, for example  new Circle(5).

//* A record is a short way to write a class that only holds data.
// This one line gives a Circle that holds a radius. Java writes the dull
// parts for you:
//   - a way to build it:        new Circle(5)
//   - a way to read the value:  circle.radius()
//   - a way to print it nicely  Circle[radius=5.0]
//   - a way to compare two      two circles with the same radius are equal
// Why it matters: this used to take about 30 lines of code.

//& "implements Shape" is a promise. We explain Shape a bit further down.
record Circle(double radius) implements Shape {}

record Rect(double width, double height) implements Shape {
    //* A record can check its values when it is built. This special part
    // has the same name as the record and no brackets. It runs first,
    // and Java fills in the values afterwards. If a check fails, we stop
    // with an error, so a bad Rect can never exist.
    Rect {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Sides must be positive");
        }
    }
}

record Triangle(double base, double height) implements Shape {}

//* An interface is a promise a type can make: "I count as this kind of
// thing". Circle, Rect and Triangle all promise to be a Shape, so a
// method that wants any Shape accepts all three.

// "sealed" closes the list. Only the types named after "permits" may make
// the promise. Nobody can sneak in a fourth shape later.
// Why that is good: Java knows the full list, so when you switch over a
// Shape (see area below), it can warn you if you forget one.
sealed interface Shape permits Circle, Rect, Triangle {}

//* An enum is a fixed list of allowed values, like in Basics.java, but
// this time we made our own. A drink can only be one of these three sizes.
enum Size { SMALL, MEDIUM, LARGE }

//! Because an enum has a fixed list, this switch needs no "default" line.
// Java checks that every size is covered. Try deleting the LARGE line and
// Java will refuse to run the file and tell you what is missing.
String drinkPrice(Size size) {
    return switch (size) {
        case SMALL  -> "$2";
        case MEDIUM -> "$3";
        case LARGE  -> "$4";
    };
}

//* This is the big one: a switch that checks the TYPE of a thing.
// "case Circle(double r)" does two jobs at once:
//   1. checks whether the shape is a Circle
//   2. takes the radius out of it and names it r
// You do not need to convert the type by hand or call a reader method.
// Shape is sealed and all three types are listed, so no default is needed.
// Add a fourth shape later and Java will make you come back here.
// `var` works too: Java works out the type for you.
double area(Shape shape) {
    return switch (shape) {
        case Circle(double r)         -> Math.PI * r * r;
        case Rect(double w, double h) -> w * h;
        case Triangle(var b, var h)   -> 0.5 * b * h;
    };
}

//* Here the thing can be anything, so the parameter type is Object.
// "when" adds an extra test to a line. Java reads the lines from the top
// and uses the first one that fits. So put the special cases first.
// For example, "Integer i when i < 0" must come before plain "Integer i",
// or the plain one would catch every number.
// "case null" is for when nothing was given at all.
//& (Integer is the object version of int. A switch on Object needs it.)
String classify(Object thing) {
    return switch (thing) {
        case null                          -> "nothing at all";
        case Integer i when i < 0          -> "negative number " + i;
        case Integer i                     -> "number " + i;
        case String s when s.isBlank()     -> "empty text";
        case String s                      -> "text with " + s.length() + " letters";
        case Circle c when c.radius() > 10 -> "a huge circle";
        case Shape s                       -> "a shape: " + s;
        default                            -> "something else: " + thing.getClass().getSimpleName();
    };
}

//* Records can sit inside records. A Line has two Points.
record Point(int x, int y) {}
record Line(Point from, Point to) {}

// The pattern can open a record that is inside another record, all in one
// line. Here we get the four numbers out of a Line and its two Points.
// The same pattern is written three times because each line has a
// different "when" test (or none).
String describeLine(Line line) {
    return switch (line) {
        case Line(Point(var x1, var y1), Point(var x2, var y2)) when x1 == x2
                -> "up and down line at x = " + x1;
        case Line(Point(var x1, var y1), Point(var x2, var y2)) when y1 == y2
                -> "flat line at y = " + y1;
        case Line(Point(var x1, var y1), Point(var x2, var y2))
                -> "slanted line from (%d,%d) to (%d,%d)".formatted(x1, y1, x2, y2);
    };
}

//* Next topic: a child class and its parent.

// "class Derived extends Base" means Derived is a child of Base. The child
// gets everything the parent has, and can add more or change things.

// A constructor is a special method that runs once when `new` builds an
// object. It has the same name as the class.

// A child is built in two steps: the parent part first, then the child
// part. "super()" is how the child tells Java: "build my parent part now".
//& (Writing `static` in front of a class that lives in this file is a
// habit for now. Without it, Java wants an object of the hidden class
// first. Just copy it.)
static class Base {
    Base() {
        //! The parent runs describe(). But if the child has its own
        // describe(), then the child's one runs instead! That surprise
        // is the trap this example shows.
        describe();
    }

    void describe() {
        IO.println("  Base.describe() ran");
    }
}

static class Derived extends Base {
    private final String label;

    Derived(String rawLabel) {
        //* NEW in Java 25. Before it, super() had to be the very first line
        // of a constructor. So you could not check anything first, and the
        // child's own values were still empty when the parent ran.

        // Now you may run code BEFORE super(). Two good uses:
        //   1. check the input and stop early if it is bad
        //   2. fill the child's own variables, so the parent sees real
        //      values and not empty ones
        //! The limit: before super() you must not use `this` to call a
        // method, because the object is not fully built yet.
        if (rawLabel == null || rawLabel.isBlank()) {
            throw new IllegalArgumentException("label required");
        }
        this.label = rawLabel.strip().toUpperCase();
        super();
    }

    //& @Override says "I am replacing a method from my parent". Java then
    // checks that the parent really has a method with this name, which
    // saves you from typing mistakes.
    @Override
    void describe() {
        IO.println("  Derived.describe() ran, label = " + label);
    }
}

void main() {

    IO.println("[1] Records");
    var circle = new Circle(5);
    IO.println(circle);                           // Circle[radius=5.0]
    IO.println(circle.equals(new Circle(5)));     // true, same radius

    // The Rect check from above in action. Java stops with an error and
    // we catch it, as in Basics.java.
    try {
        new Rect(-1, 4);
    } catch (IllegalArgumentException e) {
        IO.println("The check worked: " + e.getMessage());
    }

    IO.println("\n[2] Enum");
    for (Size size : Size.values()) {      //& values() gives every item of the enum
        IO.println(size + " costs " + drinkPrice(size));
    }

    //* "instanceof" asks: is this thing of that type? The new part is
    // "String s". If the answer is yes, Java also makes the name s for you,
    // already as a String. Old Java needed an extra line to convert it.
    // && means "and": the length check only runs when the first part is true.
    IO.println("\n[3] instanceof");
    Object mystery = "pattern matching";
    if (mystery instanceof String s && s.length() > 5) {
        IO.println("A long text: " + s.toUpperCase());
    }

    IO.println("\n[4] Switch on shapes");
    List<Shape> shapes = List.of(new Circle(2), new Rect(3, 4), new Triangle(6, 5));
    for (Shape shape : shapes) {
        IO.println("%s has area %.2f".formatted(shape, area(shape)));   //& %.2f = 2 digits after the dot
    }

    IO.println("\n[5] Switch on anything");
    Object[] samples = { 42, -7, "hello", "   ", new Circle(20), new Circle(1), 3.14, null };
    for (Object sample : samples) {
        IO.println(classify(sample));
    }

    IO.println("\n[6] Records inside records");
    IO.println(describeLine(new Line(new Point(1, 1), new Point(1, 9))));
    IO.println(describeLine(new Line(new Point(0, 4), new Point(8, 4))));
    IO.println(describeLine(new Line(new Point(0, 0), new Point(3, 3))));

    // The parent's constructor prints the child's real label, because we
    // filled it in before super().
    IO.println("\n[7] Code before super()");
    new Derived("  hello  ");

    //* Lists now have clear ways to reach both ends.
    // Old way to get the last item:  list.get(list.size() - 1)
    // New way:                       list.getLast()
    // Lists that keep their order are called "sequenced".
    // new ArrayList<>(...) makes a copy of the list that CAN be changed.
    IO.println("\n[8] Both ends of a list");
    var names = new ArrayList<>(List.of("Ana", "Ben", "Cleo", "Dev"));
    IO.println("first = " + names.getFirst() + ", last = " + names.getLast());
    IO.println("backwards = " + names.reversed());   //& a backwards view, nothing is copied
    names.addFirst("Zed");       // put one at the very front
    names.removeLast();          // take the last one away
    IO.println("after changes = " + names);

    // A LinkedHashMap is a map that remembers the order you added things in.
    var medals = new LinkedHashMap<String, Integer>();
    medals.put("gold", 1);
    medals.put("silver", 2);
    medals.putFirst("platinum", 0);    // goes to the front
    IO.println("first = " + medals.firstEntry() + ", last = " + medals.lastEntry());

    //* Remember the stream belt from Basics.java? A "gatherer" is an extra
    // step you can add to it. These ones group items into small bundles,
    // which plain streams could not do neatly.
    //   windowFixed(3)   cut the items into bundles of 3
    //   windowSliding(3) bundles of 3 that slide along one item at a time
    //   scan             a running total: each result adds the next item
    IO.println("\n[9] Stream gatherers");
    IO.println("bundles of 3   : " + Stream.of(1, 2, 3, 4, 5, 6, 7).gather(Gatherers.windowFixed(3)).toList());
    IO.println("sliding bundles: " + Stream.of(1, 2, 3, 4, 5).gather(Gatherers.windowSliding(3)).toList());
    IO.println("running total  : " + Stream.of(1, 2, 3, 4).gather(Gatherers.scan(() -> 0, Integer::sum)).toList());

    IO.println("\nDone. Next: Concurrency.java");
}
