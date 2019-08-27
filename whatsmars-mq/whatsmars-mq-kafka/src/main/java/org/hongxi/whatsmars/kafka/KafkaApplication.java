package org.hongxi.whatsmars.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;

/**
 * Created by shenhongxi on 2018/12/12.
 */
@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        kafkaTemplate.send("kafkaTest", "hello");
    }
}
