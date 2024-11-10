package com.cargoSimulation;

public class MyStack<T> {
    private DoublyLinkedList<T> list;

    public MyStack() {
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
    
}
