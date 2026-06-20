import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Group 5 – Queue Scenario 5: Café Order Queue
 *
 * Customer drink orders queue up at a café counter. The barista processes orders
 * in FIFO order (first come, first served). Mobile pre-orders can be enqueued
 * AHEAD of walk-in orders using the Deque (double-ended queue) variant, so they
 * are ready for pick-up when the customer arrives.
 *
 * Attributes per order:
 *   Order number, drink type, size, special instructions
 *
 * Key operations:
 *   enqueue      – standard walk-in order added to the BACK of the deque
 *   enqueuePreOrder  – mobile pre-order added to the FRONT of the deque
 *   dequeue      – barista takes the FRONT order to process
 *   peek         – barista previews the next order without removing it
 *   displayQueue – show the full current order queue
 */
public class CafeOrderQueue {

    // -----------------------------------------------------------------------
    // Order status constants
    // -----------------------------------------------------------------------
    enum Status { QUEUED, PROCESSING, READY }

    // -----------------------------------------------------------------------
    // Inner class representing a single café order
    // -----------------------------------------------------------------------
    static class Order {
        private int orderNumber;
        private String customerName;
        private String drinkType;
        private String size;
        private String specialInstructions;
        private boolean isMobileOrder;
        private Status status;

        public Order(int orderNumber, String customerName, String drinkType,
                     String size, String specialInstructions, boolean isMobileOrder) {
            this.orderNumber = orderNumber;
            this.customerName = customerName;
            this.drinkType = drinkType;
            this.size = size;
            this.specialInstructions = specialInstructions;
            this.isMobileOrder = isMobileOrder;
            this.status = Status.QUEUED;
        }

        public String getOrderTag() {
            return isMobileOrder ? "[MOBILE]" : "[WALK-IN]";
        }

        @Override
        public String toString() {
            return String.format("Order#%03d %-9s | %-12s | %-7s | %-6s | %-30s | %s",
                    orderNumber, getOrderTag(), customerName,
                    drinkType, size, specialInstructions, status);
        }
    }

    // -----------------------------------------------------------------------
    // CafeQueue – uses ArrayDeque to support both standard and pre-orders
    // -----------------------------------------------------------------------
    static class CafeQueue {
        private Deque<Order> deque = new ArrayDeque<>();
        private int nextOrderNum = 1;

        public CafeQueue() {
            System.out.println("=====================================================");
            System.out.println("       Sunrise Café – Order Management System        ");
            System.out.println("=====================================================\n");
        }


         //ENQUEUE (back) – standard walk-in customer order

        public Order placeWalkInOrder(String customerName, String drinkType,
                                       String size, String special) {
            Order o = new Order(nextOrderNum++, customerName, drinkType, size, special, false);
            deque.addLast(o);   // added to the BACK (FIFO)
            System.out.println("[ENQUEUE – Walk-in]  " + o);
            displayQueue();
            return o;
        }


         //ENQUEUE FRONT (front) – mobile pre-order; goes to front so it's processed first

        public Order placeMobilePreOrder(String customerName, String drinkType,
                                          String size, String special) {
            Order o = new Order(nextOrderNum++, customerName, drinkType, size, special, true);
            deque.addFirst(o);  // added to the FRONT (priority)
            System.out.println("[ENQUEUE – Mobile ]  " + o);
            displayQueue();
            return o;
        }


         // PEEK – barista previews the next order without removing it

        public void peekNextOrder() {
            if (deque.isEmpty()) {
                System.out.println("[PEEK]  Queue is empty – no pending orders.\n");
                return;
            }
            System.out.println("[PEEK]  Next to process: " + deque.peekFirst() + "\n");
        }


         // DEQUEUE – barista processes the next order (removes from front)
         // Status changes: QUEUED → PROCESSING → READY

        public void processNextOrder() {
            if (deque.isEmpty()) {
                System.out.println("[PROCESS]  No orders in queue.\n");
                return;
            }
            Order o = deque.pollFirst();
            o.status = Status.PROCESSING;
            System.out.println("[PROCESS]  Making → " + o);
            // Simulate preparation complete
            o.status = Status.READY;
            System.out.println("[READY  ]  Done    → " + o);
            displayQueue();
        }


         // DISPLAY – print every order currently waiting in the queue

        public void displayQueue() {
            System.out.println("  ┌──────────────────────────────────────────────────────────────────────────────────┐");
            System.out.printf ("  │  Queue length: %d order(s) waiting%n", deque.size());
            System.out.println("  │──────────────────────────────────────────────────────────────────────────────────│");
            if (deque.isEmpty()) {
                System.out.println("  │  (queue is empty)                                                                │");
            } else {
                int pos = 1;
                for (Order o : deque) {
                    System.out.printf("  │  [%d] %s%n", pos++, o);
                }
            }
            System.out.println("  └──────────────────────────────────────────────────────────────────────────────────┘\n");
        }
    }

    // -----------------------------------------------------------------------
    // Main – demonstration walkthrough
    // -----------------------------------------------------------------------
    public static void main(String[] args) {

        CafeQueue cafe = new CafeQueue();

        // --- Walk-in customers arrive ---
        System.out.println("=== Walk-in customers placing orders ===\n");
        cafe.placeWalkInOrder("Alice",   "Latte",      "Large",  "Oat milk, no sugar");
        cafe.placeWalkInOrder("Bob",     "Espresso",   "Small",  "Double shot");
        cafe.placeWalkInOrder("Charlie", "Cappuccino", "Medium", "Extra foam");

        // --- Mobile pre-orders jump to front ---
        System.out.println("=== Mobile pre-orders arriving (jump to front) ===\n");
        cafe.placeMobilePreOrder("Diana",   "Flat White", "Medium", "Soy milk");
        cafe.placeMobilePreOrder("Edward",  "Matcha",     "Large",  "No sweetener");

        // --- Barista peeks at the next order ---
        System.out.println("=== Barista previews next order ===\n");
        cafe.peekNextOrder();

        // --- Barista processes orders one by one ---
        System.out.println("=== Barista processes all orders ===\n");
        while (!cafe.deque.isEmpty()) {
            cafe.processNextOrder();
        }

        // --- Edge case: dequeue from empty queue ---
        System.out.println("=== Edge case: processing from empty queue ===\n");
        cafe.processNextOrder();

        System.out.println("All orders fulfilled. Café queue is clear.");
    }
}
