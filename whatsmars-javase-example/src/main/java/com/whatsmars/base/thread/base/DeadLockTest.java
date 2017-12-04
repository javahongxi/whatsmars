package com.whatsmars.base.thread.base;

public class DeadLockTest implements Runnable {
	public int flag = 1;
	
	static Object o1 = new Object();
	static Object o2 = new Object();
	
	public void run() {
		System.out.println("flag=" + flag);
		
		if (flag == 1) {
			synchronized (o1) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				synchronized (o2) {
					System.out.println("1");
				}
			}
		}
		
		if (flag == 0) {
			synchronized (o2) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				synchronized (o1) {
					System.out.println("0");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		DeadLockTest d1 = new DeadLockTest();
		DeadLockTest d2 = new DeadLockTest();
		d1.flag = 1;
		d2.flag = 0;
		Thread t1 = new Thread(d1);
		Thread t2 = new Thread(d2);
		t1.start();
		t2.start();
	}
}
