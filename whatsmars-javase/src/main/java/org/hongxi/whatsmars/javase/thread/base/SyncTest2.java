package org.hongxi.whatsmars.javase.thread.base;


public class SyncTest2 implements Runnable {
	int b = 100;
	
	public synchronized void m1() throws Exception {
		b = 1000;
		Thread.sleep(5000);
		System.out.println(b);
	} 
	
	public void m2() throws Exception {
		Thread.sleep(2500);
		b = 2000;
		System.out.println(b);
	}
	
	public void run() {
		try {
			m1();
		} catch (Exception e) {}
	}
	
	public static void main(String[] args) throws Exception {
		SyncTest2 tt = new SyncTest2();
		Thread t = new Thread(tt);
		t.start();
		tt.m2();//first 1000
		System.out.println(tt.b);//second 1000
	}
}
