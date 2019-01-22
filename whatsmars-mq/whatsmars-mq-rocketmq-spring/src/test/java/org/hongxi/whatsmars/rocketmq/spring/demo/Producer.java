package org.hongxi.whatsmars.rocketmq.spring.demo;

import org.apache.rocketmq.client.producer.SendResult;
import org.hongxi.whatsmars.rocketmq.config.spring.RocketMQTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * -Drocketmq.namesrv.addr=127.0.0.1:9876
 */
public class Producer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("mq-producer.xml");
        RocketMQTemplate rocketMQTemplate = (RocketMQTemplate) context.getBean("rocketMQTemplate");
        for (int i = 0; i < 20; i++) {
            try {
                SendResult sendResult = rocketMQTemplate.send("ROCKETMQ_SPRING_TEST", "rocketMQTemplate");
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
