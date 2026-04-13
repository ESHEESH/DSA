package activity4;

public interface Stack {
    boolean push(String data);
    String pop();
    String peek();
    boolean isEmpty();
    boolean isFull();
    void display();
}
