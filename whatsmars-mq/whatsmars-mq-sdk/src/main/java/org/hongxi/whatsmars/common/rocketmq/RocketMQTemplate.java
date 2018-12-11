package org.hongxi.whatsmars.common.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.log.ClientLogger;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenhongxi on 2018/12/11.
 */
public class RocketMQTemplate {

    private static final InternalLogger log = ClientLogger.getLog();

    private static final String DEFAULT_PRODUCER_GROUP= "common-producer";

    private static Map<String, DefaultMQProducer> producerMap = new HashMap<>();

    public static DefaultMQProducer getProducer() throws MQClientException {
        return getProducer(4, DEFAULT_PRODUCER_GROUP);
    }

    public static DefaultMQProducer getProducer(int queueNum) throws MQClientException {
        return getProducer(queueNum, DEFAULT_PRODUCER_GROUP);
    }

    public static DefaultMQProducer getProducer(String producerGroup) throws MQClientException {
        return getProducer(4, producerGroup);
    }

    public static DefaultMQProducer getProducer(int queueNum, String producerGroup) throws MQClientException {
        if (queueNum < 1) throw new IllegalArgumentException("queueNum must >= 1");
        if (StringUtils.isBlank(producerGroup)) throw new IllegalArgumentException("producerGroup cannot be null");
        String producerKey = producerGroup + queueNum;
        if (producerMap.get(producerKey) == null) {
            synchronized (producerMap) {
                if (producerMap.get(producerKey) == null) {
                    DefaultMQProducer producer = new DefaultMQProducer(DEFAULT_PRODUCER_GROUP);
                    producer.setDefaultTopicQueueNums(queueNum);
                    producer.start();
                    producerMap.put(producerKey, producer);
                }
            }
        }
        return producerMap.get(producerKey);
    }

    public static void send(String producerGroup, String topic, String tags, String keys, String body) {
        try {
            getProducer(producerGroup).send(new Message(topic, tags, keys, body.getBytes(RemotingHelper.DEFAULT_CHARSET)));
        } catch (Exception e) {
            log.error("send error, producerGroup:{}, topic:{}, tags:{}, keys:{}, body:{}",
                    producerGroup, topic, tags, keys, body);
        }
    }

    public static void sendOrderly(String producerGroup, String topic, String tags, String keys, String body) {
        try {
            getProducer(producerGroup).send(new Message(topic, tags, keys, body.getBytes(RemotingHelper.DEFAULT_CHARSET)),
                    new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            long id = NumberUtils.toLong(String.valueOf(arg));
                            int index = (int) (id % mqs.size());
                            return mqs.get(index);
                        }
                    }, keys);
        } catch (Exception e) {
            log.error("send error, producerGroup:{}, topic:{}, tags:{}, keys:{}, body:{}",
                    producerGroup, topic, tags, keys, body, e);
        }
    }
}