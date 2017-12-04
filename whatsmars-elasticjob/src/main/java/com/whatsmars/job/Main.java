package com.whatsmars.job;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by javahongxi on 2017/10/31.
 */
public class Main {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:META-INF/applicationContext.xml");
    }
}
