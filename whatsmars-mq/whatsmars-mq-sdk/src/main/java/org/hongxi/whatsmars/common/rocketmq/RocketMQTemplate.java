package org.hongxi.whatsmars.common.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

    public static DefaultMQProducer getProducer() throws Exception {
        return getProducer(4, DEFAULT_PRODUCER_GROUP);
    }

    public static DefaultMQProducer getProducer(int queueNum) throws Exception {
        return getProducer(queueNum, DEFAULT_PRODUCER_GROUP);
    }

    public static DefaultMQProducer getProducer(String producerGroup) throws Exception {
        return getProducer(4, producerGroup);
    }

    public static DefaultMQProducer getProducer(int queueNum, String producerGroup) throws Exception {
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

    public static void send(String topic, String body) {
        send(DEFAULT_PRODUCER_GROUP, topic, body);
    }

    public static void send(String producerGroup, String topic, String body) {
        send(producerGroup, topic, "", body);
    }

    public static void send(String producerGroup, String topic, String tags, String body) {
        send(producerGroup, topic, tags, "", body);
    }

    public static void send(String producerGroup, String topic, String tags, String keys, String body) {
        try {
            send(producerGroup, new Message(topic, tags, keys, body.getBytes(RemotingHelper.DEFAULT_CHARSET)));
        } catch (Exception e) {
            log.error("send error, producerGroup:{}, topic:{}, tags:{}, keys:{}, body:{}",
                    producerGroup, topic, tags, keys, body, e);
            throw new MessagingException(e.getMessage(), e);
        }
    }

    private static void send(String producerGroup, Message message) throws Exception {
        getProducer(producerGroup).send(message);
    }

    public static void sendOrderly(String producerGroup, String topic, String keys, String body) {
        sendOrderly(producerGroup, topic, keys, body);
    }

    public static void sendOrderly(String producerGroup, String topic, String tags, String keys, String body) {
        try {
            sendOrderly(producerGroup, new Message(topic, tags, keys, body.getBytes(RemotingHelper.DEFAULT_CHARSET)));
        } catch (Exception e) {
            log.error("send error, producerGroup:{}, topic:{}, tags:{}, keys:{}, body:{}",
                    producerGroup, topic, tags, keys, body, e);
            throw new MessagingException(e.getMessage(), e);
        }
    }

    private static void sendOrderly(String producerGroup, Message message) throws Exception {
        getProducer(producerGroup).send(message,
                new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        long id = NumberUtils.toLong(String.valueOf(arg));
                        int index = (int) (id % mqs.size());
                        return mqs.get(index);
                    }
                }, message.getKeys());
    }

}