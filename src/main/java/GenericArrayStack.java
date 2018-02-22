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

    /**
     * Default constuctor for the GenericArrayStack
     * 
     * @param cap The max capasity of the stack
     */
    public GenericArrayStack(int cap) {
        @SuppressWarnings("unchecked")
        E[] temp = (E[]) new Object[cap]; 
        this.stack = temp;
        this.topOfStack = -1;       
    }

    /**
     * Checks if the stack is empty
     * 
     * @return True if empty false if not
     */
    public boolean isEmpty() {
        return this.topOfStack == -1;        
    }

    /**
     * Adds a new element to the top of the stack will not add if the stack is already full
     * 
     * @param elem The element to put on the stack
     */
    public void push( E elem ) {
        if(this.topOfStack == this.stack.length - 1){
            return;
        }
        this.topOfStack++;
        this.stack[this.topOfStack] = elem;
    }

    /**
     * Removes and returns the element on the top of the stack
     * 
     * @return The element on the top of the stack null if the stack is empty
     */
    public E pop() {
        if(this.topOfStack == -1){
            return null;
        }
        this.topOfStack--;
        return this.stack[this.topOfStack + 1];
    }

    /**
     * Just looks at the top of the stack (doesn't remove)
     * 
     * @return The element at the top of the stack null if empty
     */
    public E peek() {
        if(this.topOfStack == -1){
            return null;
        }
        return this.stack[this.topOfStack];
    }
}
