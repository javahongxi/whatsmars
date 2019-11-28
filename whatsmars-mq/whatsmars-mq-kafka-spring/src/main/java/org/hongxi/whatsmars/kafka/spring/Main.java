package org.hongxi.whatsmars.kafka.spring;

import org.hongxi.whatsmars.kafka.spring.config.KafkaConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(KafkaConfiguration.class);
        context.scan("org.hongxi.whatsmars.kafka.spring.consumer");
        context.refresh();

        KafkaTemplate<String, String> kafkaTemplate = context.getBean(KafkaTemplate.class);
        kafkaTemplate.send("kafkaTest", "hello");
        System.out.println("send message: hello");
    }
}
