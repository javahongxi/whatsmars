package org.hongxi.whatsmars.base.rmi;

import java.io.Serializable;

/**
 * Created by shenhongxi on 2016/4/18.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 7105466693678286106L;
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
