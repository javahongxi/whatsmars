package org.hongxi.whatsmars.rocketmq.boot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.apache.rocketmq.spring.starter.enums.ConsumeMode;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(topic = "test-topic-4", consumerGroup = "my-consumer_test-topic-4",
    consumeMode = ConsumeMode.ORDERLY)
public class MyConsumer4 implements RocketMQListener<String> {
    public void onMessage(String message) {
        log.info("received message: " + message);
        int a = 1 / 0;
    }
}