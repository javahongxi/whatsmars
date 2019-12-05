package org.hongxi.whatsmars.kafka.spring;

import org.hongxi.whatsmars.kafka.spring.config.KafkaConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan(Application.class.getPackage().getName());
        context.refresh();

        KafkaTemplate<String, String> kafkaTemplate = context.getBean(KafkaTemplate.class);
        kafkaTemplate.send("kafkaTest", "hello");
        System.out.println("send message: hello");
    }
}
