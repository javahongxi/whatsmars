package org.hongxi.whatsmars.rocketmq.boot.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.apache.rocketmq.spring.starter.enums.MessageDelayLevel;
import org.hongxi.whatsmars.rocketmq.boot.OrderPaidEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    public static void main(String[] args){
        SpringApplication.run(ProducerApplication.class, args);
    }
    
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            try {
                rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));
        rocketMQTemplate.sendDelayed("test-topic-1", MessageBuilder.withPayload("I'm delayed message").build(), MessageDelayLevel.TIME_1M);
        rocketMQTemplate.sendOneWay("test-topic-1", MessageBuilder.withPayload("I'm one way message").build());
        rocketMQTemplate.syncSendOrderly("test-topic-4", MessageBuilder.withPayload("I'm order message").build(), "1234");
        rocketMQTemplate.asyncSend("test-topic-1", MessageBuilder.withPayload("I'm async message").build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("async: {}", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                log.error(e.getMessage(), e);
            }
        });
        System.out.println("send finished!");
        
//        rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
    }

}