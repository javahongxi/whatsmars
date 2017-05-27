package com.itlong.whatsmars.earth.support.web.service.uuid;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shenhongxi on 2016/8/12.
 */
public class Main {

    static ApplicationContext context;

    public static void init() {
        context = new ClassPathXmlApplicationContext("classpath*:spring-config.xml");
    }
    public static void main(String[] args) {
        init();
        UuidContext.init();
        UuidServiceImpl uuidService = (UuidServiceImpl) context.getBean("uuidService");
        Thread t = new TestThread("TESTPSBC", uuidService);
        Thread t1 = new TestThread("TESTPSBC", uuidService);
        Thread t2 = new TestThread("TESTPSBC", uuidService);
        Thread t3 = new TestThread("TESTPSBC", uuidService);
        Thread t4 = new TestThread("TEST5CMB", uuidService);
        Thread t5 = new TestThread("TEST5CMB", uuidService);
        Thread t6 = new TestThread("TEST5CMB", uuidService);
        Thread t7 = new TestThread("TEST5CMB", uuidService);
        t.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

    }
}
