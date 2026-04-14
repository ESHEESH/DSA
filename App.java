package activity5;

public class App {
    public static void main(String[] args) {
        Stack stack = new LinkedListStack();

        System.out.println("SCHOOL CLUB MEMBER ACTION HISTORY SYSTEM");
        System.out.println("Laboratory Activity 5: Stack Implementation Using Linked List");
        System.out.println("Stack is empty: " + stack.isEmpty());
        System.out.println("Current stack size: " + stack.getCount());
        System.out.println();

        System.out.println("Push Register Ana: " + stack.push("Register Ana"));
        System.out.println("Push Register Ben: " + stack.push("Register Ben"));
        System.out.println("Push Update Ben to Bryan: " + stack.push("Update Ben to Bryan"));
        stack.display();
        System.out.println("Top item: " + stack.peek());
        System.out.println("Current stack size: " + stack.getCount());
        System.out.println();

        System.out.println("Pop item: " + stack.pop());
        stack.display();
        System.out.println("Stack is empty: " + stack.isEmpty());
        System.out.println("Current stack size: " + stack.getCount());
        System.out.println();

        System.out.println("Push Delete Ana: " + stack.push("Delete Ana"));
        System.out.println("Push Display Members: " + stack.push("Display Members"));
        stack.display();
        System.out.println("Top item: " + stack.peek());
        System.out.println("Current stack size: " + stack.getCount());
        System.out.println();

        System.out.println("Push null action: " + stack.push(null));
        System.out.println("Pop item: " + stack.pop());
        System.out.println("Pop item: " + stack.pop());
        System.out.println("Pop item: " + stack.pop());
        System.out.println("Pop item: " + stack.pop());
        stack.display();
        System.out.println("Top item on empty stack: " + stack.peek());
        System.out.println("Pop item on empty stack: " + stack.pop());
        System.out.println("Stack is empty: " + stack.isEmpty());
        System.out.println("Current stack size: " + stack.getCount());
    }
}
