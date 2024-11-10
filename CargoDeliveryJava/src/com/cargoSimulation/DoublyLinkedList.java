package com.cargoSimulation;

public class DoublyLinkedList<T> {

    private class Node {
        T data;
        Node prev;
        Node next;

        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

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

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node current = head;

            public boolean hasNext() {
                return current != null;
            }

            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}