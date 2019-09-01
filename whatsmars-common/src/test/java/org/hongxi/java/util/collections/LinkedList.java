package org.hongxi.java.util.collections;

/**
 * @author shenhongxi 2019/8/12
 * @see java.util.LinkedList
 */
public class LinkedList<E> {

    int size;
    Node<E> head;
    Node<E> tail;

    public void add(E e) {
        final Node<E> l = tail;
        final Node<E> newNode = new Node<>(e, null);
        tail = newNode;
        if (l == null)
            head = newNode;
        else
            l.next = newNode;
        size++;
    }

    public void reverse() {
        if (head == null) return;
        tail = head;
        Node<E> pre = head;
        Node<E> curr = pre.next;
        while (curr != null) {
            pre.next = curr.next;
            curr.next = head;
            head = curr;
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
        for (Node<E> p = head; p != null; p = p.next) {
            s.append(p.item);
            if (p != tail) {
                s.append(", ");
            }
        }
        return "[" + s.toString() + "]";
    }
}
