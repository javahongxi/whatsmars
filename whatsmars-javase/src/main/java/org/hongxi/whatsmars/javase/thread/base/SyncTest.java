package org.hongxi.whatsmars.javase.thread.base;

public class SyncTest implements Runnable {
	Timer t = new Timer();
	public static void main(String[] args) {
		 SyncTest test = new SyncTest();
		 Thread t1 = new Thread(test);
		 Thread t2 = new Thread(test);
		 t1.setName("t1");
		 t2.setName("t2");
		 t1.start();
		 t2.start();
	}
	
	public void run() {
		t.add(Thread.currentThread().getName());
	}
}

class Timer {
	private static int num = 0;
	public synchronized void add(String name) {
		num++;
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {}
		System.out.println(name + ", 你是第" + num + "个使用Timer的线程");
	}
} 
