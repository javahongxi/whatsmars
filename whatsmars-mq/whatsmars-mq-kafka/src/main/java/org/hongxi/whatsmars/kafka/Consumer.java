package org.hongxi.whatsmars.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2018/12/12.
 */
@Slf4j
@Component
public class Consumer {

    @KafkaListener(topics = "kafkaTest")
    public void onMessage(String message) {
        log.info("receive message:{}", message);
    }
}
