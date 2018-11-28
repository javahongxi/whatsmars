package org.hongxi.whatsmars.mq.rocketmq.config.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class Consumer extends ClientConfig implements FactoryBean<DefaultMQPushConsumer>,InitializingBean,DisposableBean {

    private DefaultMQPushConsumer consumer;

    private String consumerGroup;

    private MessageModel messageModel = MessageModel.CLUSTERING;

    private int consumeThreadMin = 20;

    private int consumeThreadMax = 64;

    private int pullThresholdForQueue = 1000;

    private int pullThresholdSizeForQueue = 100;

    private int consumeMessageBatchMaxSize = 1;

    private int pullBatchSize = 32;

    private int maxReconsumeTimes = -1;

    private long consumeTimeout = 15;

    private String topic;

    private String tags;

    private MessageListenerConcurrently messageListener;

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setMessageModel(String messageModel) {
        this.messageModel = MessageModel.valueOf(messageModel);
    }

    public void setConsumeThreadMin(int consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public void setConsumeThreadMax(int consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public void setPullThresholdForQueue(int pullThresholdForQueue) {
        this.pullThresholdForQueue = pullThresholdForQueue;
    }

    public void setPullThresholdSizeForQueue(int pullThresholdSizeForQueue) {
        this.pullThresholdSizeForQueue = pullThresholdSizeForQueue;
    }

    public void setConsumeMessageBatchMaxSize(int consumeMessageBatchMaxSize) {
        this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
    }

    public void setPullBatchSize(int pullBatchSize) {
        this.pullBatchSize = pullBatchSize;
    }

    public void setMaxReconsumeTimes(int maxReconsumeTimes) {
        this.maxReconsumeTimes = maxReconsumeTimes;
    }

    public void setConsumeTimeout(long consumeTimeout) {
        this.consumeTimeout = consumeTimeout;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setMessageListener(MessageListenerConcurrently messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public DefaultMQPushConsumer getObject() throws Exception {
        return consumer;
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultMQPushConsumer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setInstanceName(instanceName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(messageModel);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setPullThresholdForQueue(pullThresholdForQueue);
        consumer.setPullThresholdSizeForQueue(pullThresholdSizeForQueue);
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        consumer.setPullBatchSize(pullBatchSize);
        consumer.setMaxReconsumeTimes(maxReconsumeTimes);
        consumer.setConsumeTimeout(consumeTimeout);
        consumer.subscribe(topic, tags);
        consumer.registerMessageListener(messageListener);
        consumer.start();
        log.info("Consumer Group {} started!", consumerGroup);
    }

    @Override
    public void destroy() throws Exception {
        if (consumer != null) {
            consumer.shutdown();
        }
    }
}
