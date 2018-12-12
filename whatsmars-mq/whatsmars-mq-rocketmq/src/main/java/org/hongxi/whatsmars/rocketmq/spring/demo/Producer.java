package org.hongxi.whatsmars.rocketmq.spring.demo;

import org.hongxi.whatsmars.rocketmq.config.spring.RocketMQTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Producer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/rocketmq-producer.xml");
        RocketMQTemplate rocketMQTemplate = (RocketMQTemplate) context.getBean("rocketMQTemplate");
        for (int i = 0; i < 20; i++) {
            try {
                rocketMQTemplate.send("sdk-test", "rocketMQTemplate");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
