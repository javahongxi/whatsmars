package org.hongxi.whatsmars.base.dp.iterator;


public class LinkedList implements Collection {
	Node head = null;
	Node tail = null;
	int size = 0;
	public void add(Object o) {
		Node n = new Node(o, null);
		if(head == null) {
			head = n;
			tail = n;
		}
		tail.setNext(n);
		tail = n;
		size ++;
	}
	
	public int size() {
		return size;
	}

	@Override
	public Iterator iterator() {
		return null;
	}
}
