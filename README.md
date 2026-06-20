# 🗂️ Stack & Queue Data Structures — Real-World Implementations in Java

A Data Structures & Algorithms group project demonstrating practical, real-world use cases of **Stack (LIFO)** and **Queue/Deque (FIFO)** data structures, implemented in Java.

> 📘 Module: Data Structures and Algorithms — Academic Presentation

---

## 👥 Group Members
- 025 — M I S Ahamed
- 035 — M A A Adheeb
- 005 — E G C T Wijerathna
- 036 — B H M Aftheer

---

## 🎯 Project Overview

This project explores two of the most fundamental data structures in computer science by building two real-world Java applications:

| Scenario | Data Structure | Concept Demonstrated |
|---|---|---|
| 📸 **Photo Filter Application** | Stack (`java.util.Stack`) | LIFO — apply & undo filters in reverse order |
| ☕ **Café Order Queue** | Deque (`java.util.ArrayDeque`) | FIFO + priority insertion — walk-in vs mobile pre-orders |

---

## 📸 1. Photo Filter Application

**Problem:** A photo editing app needs to apply filters (Blur, Contrast, Sepia, Vignette, Sharpen) and allow users to undo them in reverse order — without re-uploading the original photo.

**Solution:** A `Stack<Filter>` (LIFO) where:
- `applyFilter()` → **push** — adds a new filter to the top
- `removeLastFilter()` → **pop** — undoes the most recently applied filter (with empty-stack guard)
- `previewCurrentFilter()` → **peek** — views the current top filter without removing it
- `displayState()` — prints the full filter stack (bottom → top)

```java
stack.push(new Filter(name, parameter));   // applyFilter()
if (!stack.isEmpty()) stack.pop();         // removeLastFilter()
return stack.peek();                       // previewCurrentFilter()
```

**Demo flow:** Apply 5 filters → peek top (Sharpen) → undo 2 → apply 1 new filter → undo all remaining → edge case: pop on empty stack.

📄 File: [`Photo_Filter_Application.java`](./Photo_Filter_Application.java)

---

## ☕ 2. Café Order Queue

**Problem:** A busy café needs to manage walk-in customer orders fairly (FIFO) while still giving mobile pre-orders priority so they're ready when the customer arrives.

**Solution:** A `Deque<Order>` (`ArrayDeque`) where:
- `placeWalkInOrder()` → **enqueue (rear)** — `addLast()` adds standard orders to the back
- `placeMobilePreOrder()` → **enqueue (front)** — `addFirst()` jumps mobile pre-orders to the front
- `processNextOrder()` → **dequeue** — `pollFirst()` removes & processes the front order
- `peekNextOrder()` → **peek** — `peekFirst()` previews the next order without removing it
- `displayQueue()` — prints the full current order queue

```java
deque.addLast(order);    // placeWalkInOrder()
deque.addFirst(order);   // placeMobilePreOrder()
deque.pollFirst();       // processNextOrder()
```

**Demo flow:** 3 walk-in orders placed at the rear → 2 mobile pre-orders jump to the front → barista processes all orders in sequence → edge case: dequeue from an empty queue.

📄 File: [`Café_Order_Queue.java`](./Café_Order_Queue.java)

---

## 🧠 Core Concepts

| Concept | Stack | Queue / Deque |
|---|---|---|
| Principle | LIFO (Last In, First Out) | FIFO (First In, First Out) |
| Analogy | Stack of plates | Bank queue |
| Core operations | `push`, `pop`, `peek` | `enqueue`, `dequeue`, `peek` |
| Time complexity | O(1) | O(1) |
| Special variant | — | Deque allows insertion/removal at **both ends** |

### Real-World Applications
- **Stack:** Browser back/forward navigation, undo in text editors, function call stack
- **Queue:** Printer queue management, CPU task scheduling, ticket booking systems

---

## 🛠 Challenges & Lessons Learned

- Handling edge cases (empty stack pop / empty queue dequeue) without crashing the application
- Choosing **Deque over Queue** to support priority insertion via `addFirst()`
- Understanding how O(1) efficiency and visualising structure state aids debugging
- Using generic collections (`Stack<Filter>`, `Deque<Order>`) with custom classes for compile-time type safety

---

## 📁 Repository Structure

```
├── Photo_Filter_Application.java                          # Stack (LIFO) implementation
├── Café_Order_Queue.java                                   # Queue/Deque (FIFO + priority) implementation
├── Stack___Queue_Real-World_Implementations_in_Java.pdf    # Presentation slides
└── README.md
```

---

## ▶️ How to Run

```bash
javac Photo_Filter_Application.java
java PhotoFilterApplication

javac "Café_Order_Queue.java"
java CafeOrderQueue
```

Each program runs a self-contained `main()` demo that walks through the full scenario, including edge cases, and prints formatted console output showing the structure's state at every step.

---

## 📌 Conclusion

- **Stack = LIFO** — ideal for undo/redo mechanisms (`java.util.Stack`)
- **Queue/Deque = FIFO** — Deque adds priority insertion, ideal for fair service systems (`java.util.ArrayDeque`)
- All core operations (push, pop, enqueue, dequeue) run in **constant time, O(1)**
