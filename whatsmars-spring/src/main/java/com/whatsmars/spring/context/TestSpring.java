package com.whatsmars.spring.context;

import com.whatsmars.spring.model.Mars;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shenhongxi on 2016/4/7.
 */
public class TestSpring {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Mars mars = (Mars) context.getBean("mars");
        System.out.println(mars.getAge());
        System.out.println(mars.getCnName());
    }
}
