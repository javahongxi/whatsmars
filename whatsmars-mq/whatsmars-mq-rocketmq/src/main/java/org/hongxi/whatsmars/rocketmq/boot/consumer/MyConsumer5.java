package org.hongxi.whatsmars.rocketmq.boot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.apache.rocketmq.spring.starter.enums.ConsumeMode;

/**
 * 配置重试次数(本人修改点) reconsumeTimes
 */
@Slf4j
@RocketMQMessageListener(topic = "test-topic-4", consumerGroup = "my-consumer_test-topic-5",
    consumeMode = ConsumeMode.ORDERLY, reconsumeTimes = 3)
public class MyConsumer5 implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("received message: " + messageExt);
        int a = 1 / 0;
    }
}