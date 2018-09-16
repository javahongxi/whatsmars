package org.hongxi.whatsmars.javase.thread.base;

public class JoinTest {
	public static void main(String[] args) {
		MyThread2 t = new MyThread2("black horse");
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {}
		
		for (int i = 0; i < 10; i++) {
			System.out.println("I am Main Thread");
		}
	}
}

class MyThread2 extends Thread {
	public MyThread2(String s) {
		super(s);
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("I am " + getName());
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}
