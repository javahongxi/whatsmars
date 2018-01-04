package org.hongxi.whatsmars.base.thread;
public class User implements Cloneable{
	private String name;
	private int age;
	
	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof User)) {
			return false;	
		}
		User user = (User)obj;
		//if(this.name==user.name && this.age==user.age)
		if(this.name.equals(user.name) 
			&& this.age==user.age) {
			return true;
		}
		else {
			return false;
		}
	}
	public int hashCode() {
		return name.hashCode() + age;
	}
	
	public String toString() {
		return "{name:'" + name + "',age:" + age + "}";
	}
	public Object clone()  {
		Object object = null;
		try {
			object = super.clone();
		} catch (CloneNotSupportedException e) {}
		return object;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
} 
