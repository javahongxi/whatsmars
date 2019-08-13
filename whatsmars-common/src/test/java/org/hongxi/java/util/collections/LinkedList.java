package org.hongxi.java.util.collections;

/**
 * @author shenhongxi 2019/8/12
 * @see java.util.LinkedList
 */
public class LinkedList<E> {

    int size;
    Node<E> first;
    Node<E> last;

    public void add(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    public void reverse() {
        if (first == null) return;
        last = first;
        Node<E> pre = first;
        Node<E> curr = pre.next;
        while (curr != null) {
            pre.next = curr.next;
            curr.next = first;
            first = curr;
            curr = pre.next;
        }
    }

    static class Node<E> {
        E item;
        Node<E> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Node<E> p = first; p != null; p = p.next) {
            s.append(p.item);
            if (p != last) {
                s.append(", ");
            }
        }
        return "[" + s.toString() + "]";
    }
}
