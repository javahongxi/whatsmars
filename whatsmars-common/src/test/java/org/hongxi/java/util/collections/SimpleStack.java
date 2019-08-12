package org.hongxi.java.util.collections;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @author shenhongxi 2019/8/12
 *
 * @see java.util.Stack
 */
public class SimpleStack<E> {

    private int size;

    private List<E> elements = new ArrayList<>();

    public void push(E ele) {
        if (elements.size() > size) {
            elements.set(size, ele);
        } else {
            elements.add(ele);
        }
        size++;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return elements.set(--size, null);
    }

    public int size() {
        return size;
    }
}
