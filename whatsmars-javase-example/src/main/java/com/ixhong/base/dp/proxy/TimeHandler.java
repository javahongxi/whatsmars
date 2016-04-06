package com.ixhong.base.dp.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler{
	
	private Object target;



	public TimeHandler(Object target) {
		super();
		this.target = target;
	}



	@Override
	public void invoke(Object o, Method m) {
		long start = System.currentTimeMillis();
		System.out.println("starttime:" + start);
		System.out.println(o.getClass().getName());
		try {
			m.invoke(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("time:" + (end-start));
	}

}
