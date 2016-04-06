package com.ixhong.base.thread.base;


public class RunnableTest {
	public static void main(String[] args) {
		Runner1 r = new Runner1();
		Thread t = new Thread(r);
		t.start();
		
		for (int i = 0; i < 100; i++) {
			System.out.println("Main Thread ----" + i);
		}
	}
}

class Runner1 implements Runnable {
	public void run() {
		//System.out.println(Thread.currentThread().isAlive());
		for (int i = 0; i < 100; i++) {
			System.out.println("Runner1: " + i);
		}
	}
}


