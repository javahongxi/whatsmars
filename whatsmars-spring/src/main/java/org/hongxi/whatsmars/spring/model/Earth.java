package org.hongxi.whatsmars.spring.model;

/**
 * Created by shenhongxi on 2016/4/7.
 */
public class Earth {

    private long age;

    private String cnName;

    public Earth() {}

    public Earth(long age, String cnName) {
        this.age = age;
        this.cnName = cnName;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }
}
