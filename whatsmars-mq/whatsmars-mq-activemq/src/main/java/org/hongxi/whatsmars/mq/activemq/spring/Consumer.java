package org.hongxi.whatsmars.mq.activemq.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring/activemq-consumer.xml");
    }
}
