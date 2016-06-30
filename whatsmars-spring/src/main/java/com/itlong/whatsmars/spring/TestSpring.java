package com.itlong.whatsmars.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextListener;

/**
 * Created by shenhongxi on 2016/4/7.
 */
public class TestSpring {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
        Mars mars = (Mars) context.getBean("mars");
        System.out.println(mars.getAge());
        System.out.println(mars.getCnName());

        //ServletContextListener contextListener = new ContextLoaderListener();
        String s = "http://jd.com";
        System.out.println(s.substring(5, s.length()));

        Integer n = 3;
        System.out.println("3".equals(n));
    }
}
