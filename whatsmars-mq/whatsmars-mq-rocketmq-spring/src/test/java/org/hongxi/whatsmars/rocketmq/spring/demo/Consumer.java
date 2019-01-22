package org.hongxi.whatsmars.rocketmq.spring.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * -Drocketmq.namesrv.addr=127.0.0.1:9876
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("rocketmq-consumer.xml");
        context.registerShutdownHook();
    }
}
