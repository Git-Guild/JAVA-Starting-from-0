# Double-Ended Queue (Deque) — Java

A **Deque** (pronounced *"deck"*, short for **D**ouble-**E**nded **Q**ueue) is a linear data structure that allows insertion and deletion from **both** the front and the back. 

Unlike a standard Queue (FIFO: First-In, First-Out) or a Stack (LIFO: Last-In, First-Out), a Deque can act as **both** at the same time.

```
       Push Front  ────────▶  [ FRONT ]                  [ REAR ]  ◀──────── Push Back
       Pop Front   ◀────────  |   10   |   20   |   30   |   40   |  ────────▶ Pop Back
```

---

## Why Do We Need a Circular Array Deque?

If you try to build a Deque using a standard, linear array, you immediately run into an issue with memory management.

### The Linear Array Trap

Suppose we allocate an array of size 5 and push elements to the back:

```
index:    0      1      2      3      4
       +------+------+------+------+------+
       |  10  |  20  |  30  |  40  |  50  |
       +------+------+------+------+------+
          ▲                           ▲
        front                       rear
```

Now, let's pop elements from the **front** twice:

```
index:    0      1      2      3      4
       +------+------+------+------+------+
       |  X   |  X   |  30  |  40  |  50  |
       +------+------+------+------+------+
                        ▲             ▲
                      front         rear
```

Slots `0` and `1` are now empty, but `rear` is already at index `4` (the end of the array). If you try to push a new element to the back, a naive array thinks it is **full** even though 40% of its space is wasted!

---

### The Solution: Wrapping Around with Modulo Arithmetic

Instead of sliding every element to the left when popping (which would cost $O(n)$ time), we bend the array into a logical circle:

```
                  [ 0 ]
             /             \
        [ 4 ]               [ 1 ]
          |                   |
        [ 3 ]               [ 2 ]
             \             /
                  [ ... ]
```

When `rear` or `front` hits the end of the physical array, it wraps around to index `0` using the modulo operator (`%`):

- **Move Right (Clockwise):** `index = (index + 1) % capacity`
- **Move Left (Counter-Clockwise):** `index = (index - 1 + capacity) % capacity`

This allows us to reuse empty spaces left by pops in **$O(1)$ constant time**.

---

## Operations & Cost at a Glance

`█` ≈ one unit of work — longer bar = slower

| Operation | Custom Circular Deque | Java Pre-built (`ArrayDeque`) | Plain Array / ArrayList (Front ops) |
|---|---|---|---|
| **Push Front** | `██  O(1)` | `██  O(1)` | `██████████  O(n)` |
| **Push Back** | `██  O(1)` | `██  O(1)` | `██  O(1)` |
| **Pop Front** | `██  O(1)` | `██  O(1)` | `██████████  O(n)` |
| **Pop Back** | `██  O(1)` | `██  O(1)` | `██  O(1)` |
| **Resizing** | N/A (Fixed Capacity) | Amortized $O(1)$ | $O(n)$ copy |

---

## Custom Implementation vs. Java Pre-built

| Feature | Manual Circular Array (`Basics.java`) | Pre-built (`PreBuilt.java` / `ArrayDeque`) |
|---|---|---|
| **Capacity** | Fixed at initialization | Dynamic (grows automatically) |
| **Memory Allocation** | Single contiguous array | Single contiguous array (reallocated on resize) |
| **Overflow Handling** | Rejects push when full | Allocates larger array (doubles capacity) |
| **Use Case** | Systems programming, low-overhead environments, learning mechanics | Real-world applications and competitive programming |

---

## Files

Every `.java` file in this directory is self-contained and can be run independently.

| # | File | Concepts Covered | Complexity |
|---|------|------------------|------------|
| 1 | [`Basics.java`](Basics.java) | Hand-crafted circular array Deque. Demonstrates modulo arithmetic for wrap-around, tracking `currentSize`, integer reference nullifying for garbage collection, and custom pointer updates (`front` vs `rear`). | All ops $O(1)$ |
| 2 | [`PreBuilt.java`](PreBuilt.java) | Standard Java `java.util.ArrayDeque` usage. Demonstrates both inherited Queue/Stack methods (`add`, `offer`, `poll`, `peek`) and explicit Deque methods (`addLast`, `pollLast`, `peekLast`). | All ops $O(1)$ |

---

## Output

Real console output from running both files:

### **`Basics.java`**
```text
Pushed back: 1
Pushed back: 2
Pushed back: 3
Pushed back: 4
Pushed back: 5
OVERFLOW: Cannot push back 6
Deque view (Front -> Rear): 1 2 3 4 5 
OVERFLOW: Cannot push front 0
Deque view (Front -> Rear): 1 2 3 4 5 
Popped front: 1
Deque view (Front -> Rear): 2 3 4 5 
Popped back: 5
Popped back: 4
Pushed front: 5
Deque view (Front -> Rear): 5 2 3 
```

### **`PreBuilt.java`**
```text
After adding to BACK: [10, 20]
peek()      [FRONT]: 10
peekLast() [BACK]: 40
Removed via poll():      10
Removed via pollLast(): 40
Final Deque State: [20, 30]
```

---

## Next Steps

Once comfortable with Deques:
1. Try implementing a Deque using a **Doubly Linked List** (pointers to `next` and `prev`).
2. Solve classic Deque problems like **Sliding Window Maximum** or **Monotonic Queue** patterns.