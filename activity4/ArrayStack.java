package activity4;

public class ArrayStack implements Stack {
    private final String[] stack;
    private int top;

    public ArrayStack() {
        this(10);
    }

    public ArrayStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }

        stack = new String[capacity];
        top = -1;
    }

    /*
     * Algorithm:
     * 1. Check if the given data is null or the stack is full.
     * 2. If either condition is true, return false.
     * 3. Increase top by 1.
     * 4. Insert the new data at top.
     * 5. Return true.
     */
    @Override
    public boolean push(String data) {
        if (data == null || isFull()) {
            return false;
        }

        top++;
        stack[top] = data;
        return true;
    }

    /*
     * Algorithm:
     * 1. Check if the stack is empty.
     * 2. If it is empty, return null.
     * 3. Save the top item.
     * 4. Remove the top item and decrement top.
     * 5. Return the saved item.
     */
    @Override
    public String pop() {
        if (isEmpty()) {
            return null;
        }

        String item = stack[top];
        stack[top] = null;
        top--;
        return item;
    }

    /*
     * Algorithm:
     * 1. Check if the stack is empty.
     * 2. If it is empty, return null.
     * 3. Return the item at the top without removing it.
     */
    @Override
    public String peek() {
        if (isEmpty()) {
            return null;
        }

        return stack[top];
    }

    /*
     * Algorithm:
     * 1. Return true when top is -1.
     * 2. Otherwise return false.
     */
    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    /*
     * Algorithm:
     * 1. Return true when top equals the last index of the array.
     * 2. Otherwise return false.
     */
    @Override
    public boolean isFull() {
        return top == stack.length - 1;
    }

    /*
     * Algorithm:
     * 1. If the stack is empty, print the header and no items.
     * 2. Otherwise, print the header.
     * 3. Iterate from top down to 0.
     * 4. Print each stack item separated by a space.
     */
    @Override
    public void display() {
        System.out.print("Stack elements (top to bottom):");

        if (isEmpty()) {
            System.out.println();
            return;
        }

        System.out.print(" ");
        for (int i = top; i >= 0; i--) {
            System.out.print(stack[i]);
            if (i > 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
