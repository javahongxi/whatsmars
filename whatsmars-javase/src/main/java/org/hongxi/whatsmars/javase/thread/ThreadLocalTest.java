package org.hongxi.whatsmars.javase.thread;

import java.util.Random;

public class ThreadLocalTest {

	private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
	//private static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();
	public static void main(String[] args) {
		for(int i=0;i<2;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() 
							+ " has put data :" + data);
					x.set(data);
/*					MyThreadScopeData myData = new MyThreadScopeData();
					myData.setName("name" + data);
					myData.setAge(data);
					myThreadScopeData.set(myData);*/
					MyThreadScopeData.getThreadInstance().setName("name" + data);
					MyThreadScopeData.getThreadInstance().setAge(data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}
	
	static class A{
		public void get(){
			int data = x.get();
			System.out.println("A from " + Thread.currentThread().getName() 
					+ " get data :" + data);
/*			MyThreadScopeData myData = myThreadScopeData.get();;
			System.out.println("A from " + Thread.currentThread().getName() 
					+ " getMyData: " + myData.getName() + "," +
					myData.getAge());*/
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("A from " + Thread.currentThread().getName() 
					+ " getMyData: " + myData.getName() + "," +
					myData.getAge());
		}
	}
	
	static class B{
		public void get(){
			int data = x.get();			
			System.out.println("B from " + Thread.currentThread().getName() 
					+ " get data :" + data);
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("B from " + Thread.currentThread().getName() 
					+ " getMyData: " + myData.getName() + "," +
					myData.getAge());			
		}		
	}
}

class MyThreadScopeData{
	private MyThreadScopeData(){}
	public static /*synchronized*/ MyThreadScopeData getThreadInstance(){
		MyThreadScopeData instance = map.get();
		if(instance == null){
			instance = new MyThreadScopeData();
			map.set(instance);
		}
		return instance;
	}
	//private static MyThreadScopeData instance = null;//new MyThreadScopeData();
	private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();
	
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
