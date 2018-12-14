package org.hongxi.whatsmars.rocketmq.spring.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("rocketmq-consumer.xml");
        context.registerShutdownHook();
    }
}
