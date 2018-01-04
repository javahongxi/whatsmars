package org.hongxi.whatsmars.tomcat.design.adapter;

/**
 * Created by shenhongxi on 16/4/14.
 */
public class Adapter implements Target {

    Adaptee adaptee;

    public void request() {
        adaptee.specificRequest();
    }
}
