package activity4;

public class App {
    public static void main(String[] args) {
        Stack stack = new ArrayStack(4);

        System.out.println("SCHOOL CLUB MEMBER ACTION HISTORY SYSTEM");
        System.out.println("Laboratory Activity 4: Stack Implementation Using Array");
        System.out.println("Stack is empty: " + stack.isEmpty());
        System.out.println();

        System.out.println("Push Register Ana: " + stack.push("Register Ana"));
        System.out.println("Push Register Ben: " + stack.push("Register Ben"));
        System.out.println("Push Update Ben to Bryan: " + stack.push("Update Ben to Bryan"));
        stack.display();
        System.out.println("Top item: " + stack.peek());
        System.out.println();

        System.out.println("Pop item: " + stack.pop());
        stack.display();
        System.out.println("Stack is empty: " + stack.isEmpty());
        System.out.println("Stack is full: " + stack.isFull());
        System.out.println();

        System.out.println("Push Delete Ana: " + stack.push("Delete Ana"));
        System.out.println("Push Display Members: " + stack.push("Display Members"));
        stack.display();
        System.out.println("Stack is full: " + stack.isFull());
        System.out.println("Push Archive Members: " + stack.push("Archive Members"));
        System.out.println("Top item after failed push: " + stack.peek());
    }
}
