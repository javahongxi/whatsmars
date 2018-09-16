package org.hongxi.whatsmars.javase.dp.factory.abstractfac;

public class DefaultFactory extends AbstractFactory {

	@Override
	Food createFood() {
		// TODO Auto-generated method stub
		return new Apple();
	}

	@Override
	Vehicle createVehicle() {
		// TODO Auto-generated method stub
		return new Car();
	}

	@Override
	Weapon createWeapon() {
		// TODO Auto-generated method stub
		return new AK47();
	}
	
}
