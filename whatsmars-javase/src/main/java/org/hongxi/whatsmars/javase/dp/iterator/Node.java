package org.hongxi.whatsmars.javase.dp.iterator;

public class Node {
	public Node(Object data, Node next) {
		super();
		this.data = data;
		this.next = next;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	private Object data;
	private Node next;
}
