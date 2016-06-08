package com.itlong.whatsmars.base.thread.base;

import java.util.Date;

public class InterruptTest {
	public static void main(String[] args) {
		MyThread1 t = new MyThread1();
		t.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {}
		t.interrupt();
		//t.shutdown();
	}
}

class MyThread1 extends Thread {
	private boolean flag = true;
	
	public void run() {
		while (flag) {
			System.out.println(new Date());
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				return;
				//flag = false;
			}
		}
	}
	
	public void shutdown() {
		flag = false;
	}
}
