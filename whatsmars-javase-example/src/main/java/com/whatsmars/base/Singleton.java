package com.whatsmars.base;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Created by shenhongxi on 15/7/23.
 */
public class Singleton implements Serializable {

    private static final Singleton instance = new Singleton();

    private Singleton() {
        //避免反射机制,导致的多例问题,通过反射机制仍然可以对私有构造函数进行操作  ?
        if (instance != null) {
            throw new RuntimeException("instance != null");
        }
    }

    public static Singleton getInstance() {
        return instance;
    }

    private void writeObject(ObjectOutputStream out) {
        System.out.println("write object");
    }

    private void readObject(ObjectInputStream in) {
        System.out.println("read object");
    }

    public Object writeReplace() {
        System.out.println("write replace");
        return instance;
    }

    public Object readResolve() {
        System.out.println("read resolve");
        return instance;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/javahongxi/xx.txt";
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
        out.writeObject(getInstance());
        out.close();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
        Singleton o = (Singleton) in.readObject();
        System.out.println(instance.hashCode());
        System.out.println(o.hashCode());
        System.out.println(o == instance);

        System.out.println();
        Constructor[] constructors = Singleton.class.getDeclaredConstructors();
        Constructor<Singleton> c = constructors[0];
        c.setAccessible(true);
        Singleton o2 = c.newInstance();
        System.out.println("HashCode 1 : " + getInstance().hashCode());
        System.out.println("HashCode 2 : " + o2.hashCode());
        System.out.println("Equal : " + (getInstance() == o2));//打破单例
    }

}
