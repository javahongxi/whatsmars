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

    private static final String DEFAULT_PRODUCER_GROUP= "default-producer";

    private static final int DEFAULT_QUEUE_NUM = 4;

    private static final int DEFAULT_SEND_MSG_TIMEOUT = 3000;

    private static Map<String, DefaultMQProducer> producerMap = new HashMap<>();

    public static DefaultMQProducer getProducer() throws Exception {
        return getProducer(DEFAULT_QUEUE_NUM, DEFAULT_PRODUCER_GROUP, DEFAULT_SEND_MSG_TIMEOUT);
    }

    public static DefaultMQProducer getProducer(int queueNum) throws Exception {
        return getProducer(queueNum, DEFAULT_PRODUCER_GROUP, DEFAULT_SEND_MSG_TIMEOUT);
    }

    public static DefaultMQProducer getProducer(String producerGroup, int sendMsgTimeout) throws Exception {
        return getProducer(DEFAULT_QUEUE_NUM, producerGroup, sendMsgTimeout);
    }

    public static DefaultMQProducer getProducer(int queueNum, String producerGroup, int sendMsgTimeout) throws Exception {
        if (queueNum < 1) throw new IllegalArgumentException("queueNum must >= 1");
        if (StringUtils.isBlank(producerGroup)) throw new IllegalArgumentException("producerGroup cannot be null");
        String producerKey = producerGroup + queueNum;
        if (producerMap.get(producerKey) == null) {
            synchronized (producerMap) {
                if (producerMap.get(producerKey) == null) {
                    DefaultMQProducer producer = new DefaultMQProducer(DEFAULT_PRODUCER_GROUP);
                    producer.setDefaultTopicQueueNums(queueNum);
                    producer.setSendMsgTimeout(sendMsgTimeout);
                    producer.start();
                    producerMap.put(producerKey, producer);
                }
            }
        }
        return producerMap.get(producerKey);
    }

    public static void send(String topic, String body) {
        send(DEFAULT_PRODUCER_GROUP, topic, body, DEFAULT_SEND_MSG_TIMEOUT);
    }

    public static void send(String topic, String body, int sendMsgTimeout) {
        send(DEFAULT_PRODUCER_GROUP, topic, body, sendMsgTimeout);
    }

    public static void send(String producerGroup, String topic, String body, int sendMsgTimeout) {
        send(producerGroup, topic, "", body, sendMsgTimeout);
    }

    public static void send(String producerGroup, String topic, String tags, String body, int sendMsgTimeout) {
        send(producerGroup, topic, tags, "", body, sendMsgTimeout);
    }

    public static void send(String producerGroup, String topic, String tags, String keys, String body, int sendMsgTimeout) {
        try {
            send(producerGroup, new Message(topic, tags, keys, body.getBytes(RemotingHelper.DEFAULT_CHARSET)), sendMsgTimeout);
        } catch (Exception e) {
            log.error("send error, producerGroup:{}, topic:{}, tags:{}, keys:{}, body:{}",
                    producerGroup, topic, tags, keys, body, e);
            throw new MessagingException(e.getMessage(), e);
        }
    }

    private static void send(String producerGroup, Message message, int sendMsgTimeout) throws Exception {
        getProducer(producerGroup, sendMsgTimeout).send(message);
    }

    public static void sendOrderly(String producerGroup, String topic, String keys, String body) {
        sendOrderly(producerGroup, topic, keys, body, DEFAULT_SEND_MSG_TIMEOUT);
    }

    public static void sendOrderly(String producerGroup, String topic, String keys, String body, int sendMsgTimeout) {
        sendOrderly(producerGroup, topic, keys, body, sendMsgTimeout);
    }

    public static void sendOrderly(String producerGroup, String topic, String tags, String keys, String body, int sendMsgTimeout) {
        try {
            sendOrderly(producerGroup, new Message(topic, tags, keys, body.getBytes(RemotingHelper.DEFAULT_CHARSET)), sendMsgTimeout);
        } catch (Exception e) {
            log.error("send error, producerGroup:{}, topic:{}, tags:{}, keys:{}, body:{}",
                    producerGroup, topic, tags, keys, body, e);
            throw new MessagingException(e.getMessage(), e);
        }
    }

    private static void sendOrderly(String producerGroup, Message message, int sendMsgTimeout) throws Exception {
        getProducer(producerGroup, sendMsgTimeout).send(message,
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