package com.ixhong.base.dp.factory;

public class PlaneFactory extends VehicleFactory {

	@Override
	Moveable create() {
		return new Plane();
	}
}
