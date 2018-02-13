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
   
    private E[] stack;
    private int topOfStack;

   // Constructor
    public GenericArrayStack(int cap) {
        @SuppressWarnings("unchecked")
        E[] temp = (E[]) new Object[cap]; 
        this.stack = temp;
        this.topOfStack = -1;       
    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
        return this.topOfStack == 0;        
    }

    public void push( E elem ) {
        if(this.topOfStack == this.stack.length){
            return;
        }
        this.topOfStack++;
        this.stack[this.topOfStack] = elem;
    }
    public E pop() {
        if(this.topOfStack == -1){
            return null;
        }
        this.topOfStack--;
        return this.stack[this.topOfStack + 1];
    }

    public E peek() {
        return this.stack[this.topOfStack];
    }
}
