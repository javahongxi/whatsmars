package com.ixhong.base.dp.factory;

public class CarFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Car();
	}
}
