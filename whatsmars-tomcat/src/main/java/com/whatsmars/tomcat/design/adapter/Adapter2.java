package com.whatsmars.tomcat.design.adapter;

/**
 * Created by shenhongxi on 16/4/14.
 */
public class Adapter2 extends Adaptee implements Target {
    // 对于我们不必要实现的方法可在Adaptee中作空实现
    public void request() {
        super.specificRequest();
    }
}
