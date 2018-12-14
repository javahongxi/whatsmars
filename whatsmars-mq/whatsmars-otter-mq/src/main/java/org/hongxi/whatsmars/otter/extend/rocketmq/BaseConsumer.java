package org.hongxi.whatsmars.otter.extend.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.log.ClientLogger;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.logging.InternalLogger;

import java.util.List;

/**
 * Created by shenhongxi on 2018/12/11.
 * Child consumers extends BaseConsumer, and you can manage them in spring.
 */
public abstract class BaseConsumer {

    private static final InternalLogger log = ClientLogger.getLog();

    protected DefaultMQPushConsumer consumer;

    protected void startConsume(String consumerGroup, String topic) throws MQClientException {
        startConsume(consumerGroup, topic, "*");
    }

    protected void startConsume(String consumerGroup, String topic, String tags) throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.subscribe(topic, tags);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    process(msgs.get(0));
                } catch (Exception e) {
                    log.error("consume error, consumerGroup:{}, topic:{}, tags:{}", consumerGroup, topic, tags, e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }

    protected void startConsumeOrderly(String consumerGroup, String topic, String tags) throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.subscribe(topic, tags);
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                try {
                    process(msgs.get(0));
                } catch (Exception e) {
                    log.error("consume error, consumerGroup:{}, topic:{}, tags:{}", consumerGroup, topic, tags, e);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }

    protected abstract void process(MessageExt messageExt);

}
