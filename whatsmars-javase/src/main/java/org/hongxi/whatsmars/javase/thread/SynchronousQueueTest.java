package org.hongxi.whatsmars.javase.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest {

	public static void main(String[] args) {
	    //用lock-condition也可
		final Semaphore semaphore = new Semaphore(1);
		final SynchronousQueue<String> queue = new SynchronousQueue<String>();
		for(int i=0;i<10;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {	
					try {
						semaphore.acquire();
						String input = queue.take();
						String output = Doing.doSome(input);
						System.out.println(Thread.currentThread().getName()+ ":" + output);
						semaphore.release();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}).start();
		}
		
		System.out.println("begin:"+(System.currentTimeMillis()/1000 + 1));
		for(int i=0;i<10;i++){  //这行不能改动
			String input = i+"";  //这行不能改动
			try {
				queue.put(input);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

//不能改动此TestDo类
class Doing {
	public static String doSome(String input){
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String output = input + ":"+ (System.currentTimeMillis() / 1000);
		return output;
	}
}
