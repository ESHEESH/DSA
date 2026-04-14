package activity5;

public class LinkedListStack implements Stack {
    private Node top;
    private int count;

    public LinkedListStack() {
        top = null;
        count = 0;
    }

    /*
     * Algorithm:
     * 1. Check if the given data is null.
     * 2. If it is null, return false.
     * 3. Create a new node for the given data.
     * 4. Point the new node to the current top node.
     * 5. Update top to the new node.
     * 6. Increase the stack count by 1.
     * 7. Return true.
     */
    @Override
    public boolean push(String data) {
        if (data == null) {
            return false;
        }

        Node newNode = new Node(data);
        newNode.setNext(top);
        top = newNode;
        count++;
        return true;
    }

    /*
     * Algorithm:
     * 1. Check if the stack is empty.
     * 2. If it is empty, return null.
     * 3. Save the data from the top node.
     * 4. Move top to the next node.
     * 5. Decrease the stack count by 1.
     * 6. Return the saved data.
     */
    @Override
    public String pop() {
        if (isEmpty()) {
            return null;
        }

        String removedData = top.getData();
        top = top.getNext();
        count--;
        return removedData;
    }

    /*
     * Algorithm:
     * 1. Check if the stack is empty.
     * 2. If it is empty, return null.
     * 3. Return the data stored in the top node.
     */
    @Override
    public String peek() {
        if (isEmpty()) {
            return null;
        }

        return top.getData();
    }

    /*
     * Algorithm:
     * 1. Check if top is null.
     * 2. If top is null, return true.
     * 3. Otherwise, return false.
     */
    @Override
    public boolean isEmpty() {
        return top == null;
    }

    /*
     * Algorithm:
     * 1. Return the current count of nodes in the stack.
     */
    @Override
    public int getCount() {
        return count;
    }

    /*
     * Algorithm:
     * 1. Check if the stack is empty.
     * 2. If it is empty, print an empty stack message.
     * 3. Otherwise, traverse from top to bottom.
     * 4. Print each node's data separated by a space.
     * 5. Stop when the end of the linked list is reached.
     */
    @Override
    public void display() {
        if (isEmpty()) {
            System.out.println("Stack elements: [empty]");
            return;
        }

        System.out.print("Stack elements (top to bottom): ");
        Node current = top;
        while (current != null) {
            System.out.print(current.getData());
            if (current.getNext() != null) {
                System.out.print(" ");
            }
            current = current.getNext();
        }
        System.out.println();
    }
}
