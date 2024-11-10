package com.cargoSimulation;

/**
 * --------------------------------------------------------
 * Summary: Represents a first-in-first-out (FIFO) queue of objects.
 * Uses a doubly linked list as the underlying data structure.
 * --------------------------------------------------------
 */
public class MyQueue<T> {
    private DoublyLinkedList<T> list;

    /**
     * --------------------------------------------------------
     * Summary: Initializes an empty queue.
     * Precondition: None.
     * Postcondition: An empty queue is created.
     * --------------------------------------------------------
     */
    public MyQueue() {
        list = new DoublyLinkedList<>();
    }

    /**
     * --------------------------------------------------------
     * Summary: Inserts the specified item into the queue.
     * Precondition: item is not null.
     * Postcondition: The item is added to the end of the queue.
     * --------------------------------------------------------
     */
    public void enqueue(T item) {
        list.addLast(item);
    }

    /**
     * --------------------------------------------------------
     * Summary: Retrieves and removes the head of this queue.
     * Precondition: The queue is not empty.
     * Postcondition: The head item is removed from the queue and returned.
     * --------------------------------------------------------
     */
    public T dequeue() {
        return list.removeFirst();
    }

    /**
     * --------------------------------------------------------
     * Summary: Retrieves, but does not remove, the head of this queue.
     * Precondition: The queue is not empty.
     * Postcondition: The head item is returned without modifying the queue.
     * --------------------------------------------------------
     */
    public T peek() {
        return list.get(0);
    }

    /**
     * --------------------------------------------------------
     * Summary: Checks if the queue is empty.
     * Precondition: None.
     * Postcondition: Returns true if the queue is empty, false otherwise.
     * --------------------------------------------------------
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the number of items in the queue.
     * Precondition: None.
     * Postcondition: Returns the size of the queue.
     * --------------------------------------------------------
     */
    public int size() {
        return list.size();
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns an iterator over the elements in this queue.
     * Precondition: None.
     * Postcondition: Returns an iterator over the queue elements.
     * --------------------------------------------------------
     */
    public java.util.Iterator<T> iterator() {
        return list.iterator();
    }
}
