package org.hongxi.whatsmars.base.dp.proxy;


public class Client {
	public static void main(String[] args) throws Exception {
		Tank t = new Tank();
		InvocationHandler h = new TimeHandler(t);
		
		Moveable m = (Moveable)Proxy.newProxyInstance(Moveable.class, h);
		
		m.move();
	}
}
//可以对任意的对象、任意的接口方法，实现任意的代理