package HealthSense;

/**
 * Represents a stack that holds a list of hospitals.
 */
public class UndoStack {
    public Hospital[][] stack;
    public int top;
    public int maxSize;

    /**
     * Constructs a new UndoStack.
     */
    public UndoStack() {
        this.maxSize = 3;
        this.stack = new Hospital[maxSize][];
        this.top = -1;
    }

    /**
     * Checks if the stack is empty.
     */
    public boolean isEmpty(){
        return (top == -1);
    }

    /**
     * Checks if the stack is full.
     */
    public boolean isFull(){
        return (top == maxSize - 1);
    }

    /**
     * Adds a new list of hospitals to the top of the stack.
     */
    public void push(Hospital[] item){
        if (isFull()){
            reset();
        }
        stack[++top] = item;
    }

    /**
     * Removes the top most list of hospitals of the stack.
     */
    public Hospital[] pop(){
        if (isEmpty()){
            return null;
        }
        return stack[top--];
    }

    /**
     * Resets the stack so that the bottom most list of hospitals is removed from the stack.
     */
    public void reset(){
        top--;
        System.arraycopy(stack, 1, stack, 0, 2);
    }
}
