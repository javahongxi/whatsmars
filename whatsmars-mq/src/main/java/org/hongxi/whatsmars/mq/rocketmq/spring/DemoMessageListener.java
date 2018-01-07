package org.hongxi.whatsmars.mq.rocketmq.spring;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class DemoMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + messages + "%n");
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
