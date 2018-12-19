package org.hongxi.whatsmars.boot.sample.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Created by shenhongxi on 2018/12/12.
 */
@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<Object, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        kafkaTemplate.send("kafkaTest", "hello");
    }
}
