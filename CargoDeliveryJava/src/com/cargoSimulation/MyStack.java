package com.cargoSimulation;

/**
 * --------------------------------------------------------
 * Summary: Represents a last-in-first-out (LIFO) stack of objects.
 * Uses a doubly linked list as the underlying data structure.
 * Does not use iterators.
 * --------------------------------------------------------
 */
public class MyStack<T> {
    private DoublyLinkedList<T> list;

    /**
     * --------------------------------------------------------
     * Summary: Initializes an empty stack.
     * Precondition: None.
     * Postcondition: An empty stack is created.
     * --------------------------------------------------------
     */
    public MyStack() {
        list = new DoublyLinkedList<>();
    }

    /**
     * --------------------------------------------------------
     * Summary: Pushes an item onto the top of this stack.
     * Precondition: item is not null.
     * Postcondition: The item is added to the top of the stack.
     * --------------------------------------------------------
     */
    public void push(T item) {
        list.addFirst(item);
    }

    /**
     * --------------------------------------------------------
     * Summary: Removes and returns the item at the top of this stack.
     * Precondition: The stack is not empty.
     * Postcondition: The top item is removed from the stack and returned.
     * --------------------------------------------------------
     */
    public T pop() {
        return list.removeFirst();
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the item at the top of this stack without removing it.
     * Precondition: The stack is not empty.
     * Postcondition: The top item is returned without modifying the stack.
     * --------------------------------------------------------
     */
    public T peek() {
        return list.get(0);
    }

    /**
     * --------------------------------------------------------
     * Summary: Checks if the stack is empty.
     * Precondition: None.
     * Postcondition: Returns true if the stack is empty, false otherwise.
     * --------------------------------------------------------
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the number of items in the stack.
     * Precondition: None.
     * Postcondition: Returns the size of the stack.
     * --------------------------------------------------------
     */
    public int size() {
        return list.size();
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns an array containing all of the elements in this stack.
     * Precondition: None.
     * Postcondition: Returns an array of stack elements.
     * --------------------------------------------------------
     */
    public Object[] toArray() {
        return list.toArray();
    }
}
