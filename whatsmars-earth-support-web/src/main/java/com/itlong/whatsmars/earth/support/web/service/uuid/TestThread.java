package com.itlong.whatsmars.earth.support.web.service.uuid;

public class TestThread extends Thread{

	String name;
	UuidServiceImpl us;
	public TestThread(String name, UuidServiceImpl us) {
		this.name = name;
		this.us = us;
	}
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			System.out.println(Thread.currentThread().getName() + ":" + us.nextUuid(name));
		}
	}
}
