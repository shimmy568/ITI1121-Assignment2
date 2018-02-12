import java.util.LinkedList;

/**
 * A stack wrapper for the LinkedList object
 * It allows for you to put in and take elements out in a last in first out manner
 * 
 * @author Owen Anderson
 * Student number: 300011168
 * Course: ITI 1121-A
 * Assignment: 2
 *
 */
public class GenericArrayStack<E> implements Stack<E> {
   
    private LinkedList<E> stack;

   // Constructor
    public GenericArrayStack() {
        this.stack = new LinkedList<E>();        
    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
        return this.stack.size() == 0;        
    }

    public void push( E elem ) {
        this.stack.add(elem);        
    }
    public E pop() {
        return this.stack.pop();
    }

    public E peek() {
        return this.stack.peek();
    }
}
