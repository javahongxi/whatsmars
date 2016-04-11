package com.whatsmars.tomcat.connector;

import java.io.InputStream;

/**
 * Created by shenhongxi on 16/4/11.
 */
public class SocketInputStream {

    private InputStream input;

    private int size;

    public SocketInputStream(InputStream input, int size) {
        this.input = input;
        this.size = size;
    }
}
