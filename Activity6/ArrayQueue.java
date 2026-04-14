package Activity6;

public class ArrayQueue {
    String[] queue;
    int count;
    int front;
    int rear;


    public ArrayQueue(){
        this(10);

    }
    public ArrayQueue(int capacity){
        queue = new String[capacity];
        count = 0;
        rear = -1;
        front = 0;
    }

    boolean dequeue(){
        if(count == 0){
            return false;
        }
        
        queue[front] = null;
        front++;
        count--;
        return true;
    }

    boolean enqueue(String data){
        if(data == null){
            return false;
        }
        if(count == 0){
            rear = 0;
            front = 0;    
        }else if(rear == queue.length - 1){
            return false;

        }
        queue[count] = data;
        
        count++;
        return true;
    }
}
