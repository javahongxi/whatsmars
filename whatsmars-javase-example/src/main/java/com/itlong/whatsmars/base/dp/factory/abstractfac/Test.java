package com.itlong.whatsmars.base.dp.factory.abstractfac;

public class Test {
	public static void main(String[] args) {
		AbstractFactory af = new MagicFactory();
		Vehicle v = af.createVehicle();
		v.run();
	}
}
