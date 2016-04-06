package com.ixhong.base.dp.factory.abstractfac;

public class MagicFactory extends AbstractFactory {

	@Override
	Food createFood() {
		// TODO Auto-generated method stub
		return new Mushroom();
	}

	@Override
	Vehicle createVehicle() {
		// TODO Auto-generated method stub
		return new Broom();
	}

	@Override
	Weapon createWeapon() {
		// TODO Auto-generated method stub
		return new MagicStick();
	}
	
}
