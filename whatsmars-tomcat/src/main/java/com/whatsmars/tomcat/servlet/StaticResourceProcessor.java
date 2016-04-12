package com.whatsmars.tomcat.servlet;

import java.io.IOException;

/**
 * Created by shenhongxi on 2016/4/12.
 */
public class StaticResourceProcessor {

    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
