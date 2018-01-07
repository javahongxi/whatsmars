package org.hongxi.whatsmars.mq.activemq.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class Producer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/activemq-producer.xml");
        JmsTemplate defaultProducer = (JmsTemplate) context.getBean("defaultMessageProducer");
        defaultProducer.convertAndSend("Hello, ActiveMQ");
    }
}
