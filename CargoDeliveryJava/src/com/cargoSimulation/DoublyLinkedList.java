package com.cargoSimulation;

/**
 * --------------------------------------------------------
 * Summary: Represents a generic doubly linked list.
 * Provides methods to add, remove, and access elements from both ends and at specific positions.
 * --------------------------------------------------------
 */
public class DoublyLinkedList<T> {

    /**
     * --------------------------------------------------------
     * Summary: Inner class representing a node in the doubly linked list.
     * Contains data and references to the previous and next nodes.
     * --------------------------------------------------------
     */
    private class Node {
        T data;
        Node prev;
        Node next;

        /**
         * --------------------------------------------------------
         * Summary: Constructs a new node with the given data.
         * Precondition: data is not null.
         * Postcondition: A new node is created with prev and next set to null.
         * --------------------------------------------------------
         */
        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /**
     * --------------------------------------------------------
     * Summary: Initializes an empty doubly linked list.
     * Precondition: None.
     * Postcondition: An empty list is created with head and tail set to null.
     * --------------------------------------------------------
     */
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * --------------------------------------------------------
     * Summary: Adds an element to the beginning of the list.
     * Precondition: data is not null.
     * Postcondition: The element is added at the front of the list.
     * --------------------------------------------------------
     */
    public void addFirst(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * --------------------------------------------------------
     * Summary: Adds an element to the end of the list.
     * Precondition: data is not null.
     * Postcondition: The element is added at the end of the list.
     * --------------------------------------------------------
     */
    public void addLast(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * --------------------------------------------------------
     * Summary: Removes and returns the first element of the list.
     * Precondition: The list is not empty.
     * Postcondition: The first element is removed and returned; size is decremented.
     * --------------------------------------------------------
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node temp = head;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return temp.data;
    }

    /**
     * --------------------------------------------------------
     * Summary: Removes and returns the last element of the list.
     * Precondition: The list is not empty.
     * Postcondition: The last element is removed and returned; size is decremented.
     * --------------------------------------------------------
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node temp = tail;
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return temp.data;
    }

    /**
     * --------------------------------------------------------
     * Summary: Removes and returns the element at the specified index.
     * Precondition: index is between 0 and size - 1.
     * Postcondition: The element at the index is removed and returned; size is decremented.
     * --------------------------------------------------------
     */
    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node current;
            int i;
            if (index < size / 2) {
                current = head;
                i = 0;
                while (i < index) {
                    current = current.next;
                    i++;
                }
            } else {
                current = tail;
                i = size - 1;
                while (i > index) {
                    current = current.prev;
                    i--;
                }
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
            return current.data;
        }
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the element at the specified index without removing it.
     * Precondition: index is between 0 and size - 1.
     * Postcondition: The element at the index is returned.
     * --------------------------------------------------------
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node current;
        int i;
        if (index < size / 2) {
            current = head;
            i = 0;
            while (i < index) {
                current = current.next;
                i++;
            }
        } else {
            current = tail;
            i = size - 1;
            while (i > index) {
                current = current.prev;
                i--;
            }
        }
        return current.data;
    }

    /**
     * --------------------------------------------------------
     * Summary: Checks if the list is empty.
     * Precondition: None.
     * Postcondition: Returns true if the list is empty, false otherwise.
     * --------------------------------------------------------
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns the number of elements in the list.
     * Precondition: None.
     * Postcondition: Returns the size of the list.
     * --------------------------------------------------------
     */
    public int size() {
        return size;
    }

    /**
     * --------------------------------------------------------
     * Summary: Returns an iterator over the elements in this list.
     * Precondition: None.
     * Postcondition: Returns an iterator starting at the head of the list.
     * --------------------------------------------------------
     */
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node current = head;

            /**
             * --------------------------------------------------------
             * Summary: Checks if there is a next element in the iteration.
             * Precondition: None.
             * Postcondition: Returns true if there is a next element, false otherwise.
             * --------------------------------------------------------
             */
            public boolean hasNext() {
                return current != null;
            }

            /**
             * --------------------------------------------------------
             * Summary: Returns the next element in the iteration.
             * Precondition: hasNext() returns true.
             * Postcondition: Returns the next element and advances the iterator.
             * --------------------------------------------------------
             */
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}
