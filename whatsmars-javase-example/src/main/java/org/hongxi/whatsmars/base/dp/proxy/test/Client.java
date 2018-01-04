package org.hongxi.whatsmars.base.dp.proxy.test;

import org.hongxi.whatsmars.base.dp.proxy.InvocationHandler;
import org.hongxi.whatsmars.base.dp.proxy.Proxy;

public class Client {
	public static void main(String[] args) throws Exception {
		UserMgr mgr = new UserMgrImpl();
		InvocationHandler h = new TransactionHandler(mgr);
		//TimeHandler h2 = new TimeHandler(h);
		UserMgr u = (UserMgr) Proxy.newProxyInstance(UserMgr.class, h);
		u.addUser();
	}
}
