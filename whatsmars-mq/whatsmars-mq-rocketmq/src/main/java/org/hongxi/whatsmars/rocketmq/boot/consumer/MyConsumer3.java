package org.hongxi.whatsmars.rocketmq.boot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * 指定连接某个MQ集群
 */
@ConditionalOnProperty(prefix = "trade.mq", value = {"nameServer"})
@Slf4j
@RocketMQMessageListener(nameServer = "${trade.mq.nameServer}", instanceName = "${trade.mq.clusterName}", topic = "test-topic-3", consumerGroup = "my-consumer_test-topic-3")
public class MyConsumer3 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received message: " + message);
    }
}