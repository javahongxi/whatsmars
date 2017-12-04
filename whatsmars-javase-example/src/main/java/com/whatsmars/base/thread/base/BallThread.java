package com.whatsmars.base.thread.base;

public class BallThread extends Thread {
	public static void main(String[] args) {
		BallThread t = new BallThread();
		t.start();
	}
	
	public void run() {
		for (int t = 1; t <= 10; t++) {
			double y = 9.8 * t * t / 2;
			y /= 100;
			
			String ball = "          o";
			for (int i = 0; i < Math.round(y); i++) {
				System.out.println();
			}
			System.out.println(ball);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
