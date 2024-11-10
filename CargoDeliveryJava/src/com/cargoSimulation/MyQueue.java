package com.cargoSimulation;

public class MyQueue<T> {
    private DoublyLinkedList<T> list;

    public MyQueue() {
        list = new DoublyLinkedList<>();
    }

    public void enqueue(T item) {
        list.addLast(item);
    }

    public T dequeue() {
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
        
    };
        
}