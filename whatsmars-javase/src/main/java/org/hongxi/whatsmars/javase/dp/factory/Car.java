package org.hongxi.whatsmars.javase.dp.factory;

public class Car implements Moveable {
	private static Car car = new Car();//单例
	//private static List<Car> cars = new ArrayList<Car>();//多例
	
	/*private Car() {}
	
	public static Car getInstance() {
		return car;
	}*/
	
	public Car() {}

	public void run() {
		System.out.println("car running...");
	}

}
