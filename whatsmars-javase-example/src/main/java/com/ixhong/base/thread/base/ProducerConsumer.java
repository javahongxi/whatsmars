package com.ixhong.base.thread.base;

public class ProducerConsumer {
	public static void main(String[] args) {
		SyncStack ss = new SyncStack();
		
		new Thread(new Producer(ss, "p1")).start();
		new Thread(new Consumer(ss, "c1")).start();
		new Thread(new Producer(ss, "p2")).start();
		new Thread(new Consumer(ss, "c2")).start();
	}
}

class WoTou {
	private int id;
	
	public WoTou(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "WT" + getId();
	}
}

class SyncStack {
	private int index = 0;
	private WoTou[] arrWT = new WoTou[6];
	
	public synchronized void push(WoTou wt) {
		while (index == arrWT.length) {
			try {
				this.wait();
			} catch (InterruptedException e) {}
		}
		this.notifyAll();
		arrWT[index++] = wt;
	}
	
	public synchronized WoTou pop() {
		while (index == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {}
		}
		this.notifyAll();
		return arrWT[--index];
	}
}

class Producer implements Runnable {
	private SyncStack ss = null;
	private String name;
	
	public Producer(SyncStack ss, String name) {
		this.ss = ss;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void run() {
		for (int i = 0; i < 60; i++) {
			WoTou wt = new WoTou(i);
			ss.push(wt);
			System.out.println(getName() + "生产" + wt);
			try {
				Thread.sleep((long) (Math.random() * 100));
			} catch (InterruptedException e) {}
		}
	}
}

class Consumer implements Runnable {
    private SyncStack ss = null;
    private String name;
	
	public Consumer(SyncStack ss, String name) {
		this.ss = ss;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void run() {
		for (int i = 0; i < 60; i++) {
			WoTou wt = ss.pop();
			System.out.println(getName() + "消费" + wt);
			try {
				Thread.sleep((long) (Math.random() * 400));
			} catch (InterruptedException e) {}
		}
	}
}
