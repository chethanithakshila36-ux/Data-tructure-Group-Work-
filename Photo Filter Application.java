import java.util.Stack;

/**
 * Group 5 – Stack Scenario 5: Photo Filter Application
 *
 * Filters applied to a photo (blur, contrast, sepia, etc.) are pushed onto a stack.
 * Removing the last filter pops it; users can peel back filters in reverse order
 * without re-uploading the original photo.
 *
 * Key operations:
 *   push  – apply a new filter
 *   pop   – remove (undo) the most recently applied filter
 *   peek  – preview the current top filter without removing it
 *   display – show the full filter stack and the cumulative filter list
 */
public class PhotoFilterApplication {

    // -----------------------------------------------------------------------
    // Inner class representing a single photo filter
    // -----------------------------------------------------------------------
    static class Filter {
        private String name;        // e.g. "Blur", "Sepia"
        private String parameter;  // e.g. intensity, amount (descriptive string)

        public Filter(String name, String parameter) {
            this.name = name;
            this.parameter = parameter;
        }

        public String getName() { return name; }
        public String getParameter() { return parameter; }

        @Override
        public String toString() {
            return name + " [" + parameter + "]";
        }
    }

    // -----------------------------------------------------------------------
    // FilterStack – wraps Java's built-in Stack<Filter>
    // -----------------------------------------------------------------------
    static class FilterStack {
        private Stack<Filter> stack = new Stack<>();
        private String photoName;

        public FilterStack(String photoName) {
            this.photoName = photoName;
            System.out.println("=================================================");
            System.out.println(" Photo Filter Application");
            System.out.println(" Photo loaded: \"" + photoName + "\"");
            System.out.println("=================================================\n");
        }


         //PUSH – apply a filter on top of the stack

        public void applyFilter(String name, String parameter) {
            Filter f = new Filter(name, parameter);
            stack.push(f);
            System.out.println("[APPLY]  Filter pushed  → " + f);
            displayState();
        }


         //POP – remove / undo the most recently applied filter

        public void removeLastFilter() {
            if (stack.isEmpty()) {
                System.out.println("[UNDO]   No filters to remove. The photo is already in its original state.\n");
                return;
            }
            Filter removed = stack.pop();
            System.out.println("[UNDO]   Filter popped  ← " + removed);
            displayState();
        }


         //PEEK – preview the current top filter without modifying the stack

        public void previewCurrentFilter() {
            if (stack.isEmpty()) {
                System.out.println("[PEEK]   No filters applied yet.\n");
                return;
            }
            System.out.println("[PEEK]   Current top filter: " + stack.peek() + "\n");
        }


         //DISPLAY – print the entire filter stack (bottom → top) and size

        public void displayState() {
            System.out.println("  ┌─────────────────────────────────────────┐");
            System.out.println("  │  Filter Stack for: \"" + photoName + "\"");
            System.out.println("  │  Total filters applied: " + stack.size());
            System.out.println("  │─────────────────────────────────────────│");
            if (stack.isEmpty()) {
                System.out.println("  │  (empty – showing original photo)       │");
            } else {
                // Print from bottom to top; mark the top
                for (int i = 0; i < stack.size(); i++) {
                    String marker = (i == stack.size() - 1) ? " ← TOP" : "      ";
                    System.out.printf("  │  [%d] %-30s%s%n", i + 1, stack.get(i), marker);
                }
            }
            System.out.println("  └─────────────────────────────────────────┘\n");
        }

        // Returns true when no filters are applied
        public boolean isOriginal() { return stack.isEmpty(); }
    }

    // -----------------------------------------------------------------------
    // Main – demonstration walkthrough
    // -----------------------------------------------------------------------
    public static void main(String[] args) {

        FilterStack editor = new FilterStack("vacation_sunset.jpg");

        // --- Apply a series of filters ---
        System.out.println("--- Applying filters one by one ---\n");
        editor.applyFilter("Blur",       "radius=5px");
        editor.applyFilter("Contrast",   "level=+30%");
        editor.applyFilter("Sepia",      "intensity=80%");
        editor.applyFilter("Vignette",   "strength=medium");
        editor.applyFilter("Sharpen",    "amount=1.5");

        // --- Peek at the top filter ---
        System.out.println("--- Preview current (top) filter ---\n");
        editor.previewCurrentFilter();

        // --- Undo last two filters ---
        System.out.println("--- Removing last two filters (undo) ---\n");
        editor.removeLastFilter();  // removes Sharpen
        editor.removeLastFilter();  // removes Vignette

        // --- Peek again ---
        System.out.println("--- Preview after two undos ---\n");
        editor.previewCurrentFilter();

        // --- Apply a replacement filter ---
        System.out.println("--- Applying a new filter after undo ---\n");
        editor.applyFilter("Brightness", "value=+15");

        // --- Undo all remaining filters one by one ---
        System.out.println("--- Undoing all filters to restore original ---\n");
        while (!editor.isOriginal()) {
            editor.removeLastFilter();
        }

        // --- Attempt to undo from empty stack (edge case) ---
        System.out.println("--- Edge case: undo on empty stack ---\n");
        editor.removeLastFilter();

        System.out.println("Demo complete. Original photo restored.");
    }
}