package com.itlong.whatsmars.base.thread.base;

public class PriorityTest {
	public static void main(String[] args) {
		MyThread3 t1 = new MyThread3("t1");
		MyThread3 t2 = new MyThread3("t2");
		t1.setPriority(Thread.NORM_PRIORITY + 3);
		t1.start();
		t2.start();
	}
}

class MyThread4 extends Thread {
	public MyThread4(String s) {
		super(s);
	}
	
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(getName() + ":" + i);
		}
	}
}
