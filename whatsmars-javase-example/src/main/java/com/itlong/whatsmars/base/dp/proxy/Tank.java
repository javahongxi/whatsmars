package com.itlong.whatsmars.base.dp.proxy;

import java.util.Random;


public class Tank implements Moveable {

	@Override
	public void move() {
		
		System.out.println("Tank Moving...");
		try {
			Thread.sleep(new Random().nextInt(10000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	
	
}
