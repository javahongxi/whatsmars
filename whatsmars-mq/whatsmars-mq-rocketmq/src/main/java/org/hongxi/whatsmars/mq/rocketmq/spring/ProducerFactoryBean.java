package org.hongxi.whatsmars.mq.rocketmq.spring;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class ProducerFactoryBean extends ClientConfig implements FactoryBean<DefaultMQProducer>,InitializingBean,DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ProducerFactoryBean.class);

    private DefaultMQProducer producer;

    private String producerGroup;

    private volatile int defaultTopicQueueNums = 4;

    private int sendMsgTimeout = 3000;

    private int retryTimesWhenSendFailed = 2;

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setDefaultTopicQueueNums(int defaultTopicQueueNums) {
        this.defaultTopicQueueNums = defaultTopicQueueNums;
    }

    public void setSendMsgTimeout(int sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public void setRetryTimesWhenSendFailed(int retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }

    @Override
    public DefaultMQProducer getObject() throws Exception {
        return producer;
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultMQProducer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultMQProducer(producerGroup);
        producer.setInstanceName(instanceName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setDefaultTopicQueueNums(defaultTopicQueueNums);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
        producer.start();
        logger.info("Producer Group {} started!", producerGroup);
    }

    @Override
    public void destroy() throws Exception {
        if (producer != null) {
            producer.shutdown();
        }
    }
}
