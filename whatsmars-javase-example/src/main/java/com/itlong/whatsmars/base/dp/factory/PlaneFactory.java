package com.itlong.whatsmars.base.dp.factory;

public class PlaneFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Plane();
	}
}
