package org.hongxi.whatsmars.rocketmq.boot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.hongxi.whatsmars.rocketmq.boot.OrderPaidEvent;
import org.springframework.stereotype.Service;

/**
 * 指定连接某个MQ集群
 */

@Slf4j
@Service
@RocketMQMessageListener(nameServer = "127.0.0.1:9877", instanceName = "tradeCluster", topic = "test-topic-3", consumerGroup = "my-consumer_test-topic-3")
public class MyConsumer3 implements RocketMQListener<String> {
    public void onMessage(String message) {
        log.info("received message: " + message);
    }
}