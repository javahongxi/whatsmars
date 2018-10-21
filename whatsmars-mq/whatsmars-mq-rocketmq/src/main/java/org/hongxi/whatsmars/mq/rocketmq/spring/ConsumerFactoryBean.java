package org.hongxi.whatsmars.mq.rocketmq.spring;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class ConsumerFactoryBean implements FactoryBean<DefaultMQPushConsumer>,InitializingBean,DisposableBean {

    private DefaultMQPushConsumer consumer;

    private String instanceName = System.getProperty("rocketmq.client.name", "DEFAULT");

    private String consumerGroup;

    private String namesrvAddr;

    private String topic;

    private String tags;

    private MessageListenerConcurrently messageListener;

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
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
        consumer.subscribe(topic, tags);
        consumer.registerMessageListener(messageListener);
        consumer.start();
    }

    @Override
    public void destroy() throws Exception {
        consumer.shutdown();
    }
}
