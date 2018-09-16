package org.hongxi.whatsmars.javase.dp.factory;

public class CarFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Car();
	}
}
