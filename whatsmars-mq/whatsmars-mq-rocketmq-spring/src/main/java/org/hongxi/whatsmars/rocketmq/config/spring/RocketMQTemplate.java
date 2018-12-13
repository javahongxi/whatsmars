package org.hongxi.whatsmars.rocketmq.config.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * Created by shenhongxi on 2018/12/12.
 */
@Slf4j
public class RocketMQTemplate {

    private DefaultMQProducer defaultMQProducer;

    public RocketMQTemplate(DefaultMQProducer producer) {
        this.defaultMQProducer = producer;
    }

    public SendResult send(String topic, String body) {
        return send(topic, "", body);
    }

    public SendResult send(String topic, String tags, String body) {
        return send(topic, tags, "", body);
    }

    public SendResult send(String topic, String tags, String keys, String body) {
        try {
            return send(new Message(topic, tags, keys, body.getBytes(RemotingHelper.DEFAULT_CHARSET)));
        } catch (Exception e) {
            log.error("send error, topic:{}, tags:{}, keys:{}, body:{}",
                    topic, tags, keys, body, e);
            throw new MessagingException(e.getMessage(), e);
        }
    }

    private SendResult send(Message message) throws Exception {
        SendResult sendResult = this.defaultMQProducer.send(message);
        log.debug("send result: {}", sendResult);
        return sendResult;
    }

    public SendResult sendOrderly(String topic, String keys, String body) {
        return sendOrderly(topic, "", keys, body);
    }

    public SendResult sendOrderly(String topic, String tags, String keys, String body) {
        try {
            return sendOrderly(new Message(topic, tags, keys, body.getBytes(RemotingHelper.DEFAULT_CHARSET)));
        } catch (Exception e) {
            log.error("send error, topic:{}, tags:{}, keys:{}, body:{}",
                    topic, tags, keys, body, e);
            throw new MessagingException(e.getMessage(), e);
        }
    }

    private SendResult sendOrderly(Message message) throws Exception {
        SendResult sendResult = this.defaultMQProducer.send(message,
                new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        long id = NumberUtils.toLong(String.valueOf(arg));
                        int index = (int) (id % mqs.size());
                        return mqs.get(index);
                    }
                }, message.getKeys());
        log.debug("send result: {}", sendResult);
        return sendResult;
    }

}
