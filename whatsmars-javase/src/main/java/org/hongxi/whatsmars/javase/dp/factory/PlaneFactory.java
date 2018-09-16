package org.hongxi.whatsmars.javase.dp.factory;

public class PlaneFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Plane();
	}
}
