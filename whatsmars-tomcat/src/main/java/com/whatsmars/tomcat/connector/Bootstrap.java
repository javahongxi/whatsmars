package com.whatsmars.tomcat.connector;

/**
 * Created by shenhongxi on 16/4/11.
 */
public final class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
