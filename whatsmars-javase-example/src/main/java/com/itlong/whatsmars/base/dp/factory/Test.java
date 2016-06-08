package com.itlong.whatsmars.base.dp.factory;

public class Test {
	public static void main(String[] args) {
		//Car c = Car.getInstance();
		//Car c2 = Car.getInstance();//c2==c
		
		VehicleFactory vf = new PlaneFactory();
		Moveable m = vf.create();
		m.run();
	}
}
