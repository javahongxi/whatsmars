package com.whatsmars.mars001.web.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shenhongxi on 2016/4/7.
 */
public class TestSpring {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
        Mars mars = (Mars) context.getBean("mars");
        System.out.println(mars.getAge());
        System.out.println(mars.getCnName());
    }
}
