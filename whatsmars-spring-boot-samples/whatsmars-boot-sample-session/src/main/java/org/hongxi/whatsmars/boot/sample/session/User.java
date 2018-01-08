package org.hongxi.whatsmars.boot.sample.session;

import java.io.Serializable;

/**
 * Created by shenhongxi on 2018/1/8.
 */
public class User implements Serializable {
    private static final long serialVersionUID = -6332350121099606299L;

    private String id;

    private String name;

    private int gender;

    private int age;

    public User(String id, String name, int gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
