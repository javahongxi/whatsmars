package org.hongxi.java.util.concurrent;

/**
 * @author shenhongxi 2019/8/13
 */
public class BlockingStack<E> {
    private static final int DEFAULT_CAPACITY = 10;

    Object[] items;
    int size;

    BlockingStack() {
        items = new Object[DEFAULT_CAPACITY];
    }

    BlockingStack(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        items = new Object[capacity];
    }

    public synchronized void push(E item) {
        while (size == items.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        this.notifyAll();
        items[size++] = item;
    }

    public synchronized E pop() {
        while (size == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        this.notifyAll();
        return (E) items[--size];
    }
}
