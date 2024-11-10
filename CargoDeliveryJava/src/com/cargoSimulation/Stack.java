package com.cargoSimulation;

public class Stack<T> {
    private DoublyLinkedList<T> list;

    public Stack() {
        list = new DoublyLinkedList<>();
    }

    public void push(T item) {
        list.addFirst(item);
    }

    public T pop() {
        return list.removeFirst();
    }

    public T peek() {
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public java.util.Iterator<T> iterator() {
        return list.iterator();
    }
}