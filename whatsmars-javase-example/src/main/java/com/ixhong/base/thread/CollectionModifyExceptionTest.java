package com.ixhong.base.thread;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyExceptionTest {
	public static void main(String[] args) {
		Collection<User> users = new CopyOnWriteArrayList<User>();
			
		users.add(new User("张三",28));	
		users.add(new User("李四",25));			
		users.add(new User("王五",31));	
		Iterator<User> itrUsers = users.iterator();
		while(itrUsers.hasNext()){
			System.out.println("aaaa");
			User user = (User) itrUsers.next();
			if("李四".equals(user.getName())){
				users.remove(user);
				//itrUsers.remove();
			} else {
				System.out.println(user);				
			}
		}
	}
}	 
