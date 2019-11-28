package org.hongxi.whatsmars.kafka.spring.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = "kafkaTest")
    public void onMessage(String message) {
        System.out.println("receive message: " + message);
    }
}
