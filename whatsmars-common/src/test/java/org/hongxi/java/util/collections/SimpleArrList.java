package org.hongxi.java.util.collections;

import java.util.Arrays;

/**
 * @author shenhongxi 2019/8/12
 */
public class SimpleArrList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    Object[] elements;
    int size;

    SimpleArrList() {
        elements = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    void add(E e) {
        ensureCapacityInternal(size + 1);
        elements[size++] = e;
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (elements == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity - elements.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    @Override
    public String toString() {
        if (size > 0) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < size; i++) {
                s.append(elements[i]);
                if (i < size - 1) {
                    s.append(", ");
                }
            }
            return "[" + s + "]";
        }
        return "[]";
    }
}
