package org.hongxi.whatsmars.base.dp.proxy.test;

public class UserMgrImpl implements UserMgr {

	@Override
	public void addUser() {
		System.out.println("1: 插入记录到 user 表");
		System.out.println("2: 做日志记录到另一张表");
	}
	
}
