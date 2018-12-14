package org.hongxi.whatsmars.rocketmq.boot.producer;

import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.apache.rocketmq.spring.starter.enums.MessageDelayLevel;
import org.hongxi.whatsmars.rocketmq.boot.OrderPaidEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    public static void main(String[] args){
        SpringApplication.run(ProducerApplication.class, args);
    }
    
    public void run(String... args) throws Exception {
        for (int i = 0; i < 20; i++) {
            try {
                rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
                rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
                rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));
                rocketMQTemplate.sendDelayed("test-topic-1", MessageBuilder.withPayload("I'm delayed message").build(), MessageDelayLevel.TIME_1M.level);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
//        rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
    }

}