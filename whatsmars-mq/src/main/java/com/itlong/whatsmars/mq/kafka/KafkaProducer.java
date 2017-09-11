package com.itlong.whatsmars.mq.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProducer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("metadata.broker.list", "127.0.0.1:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks","-1");

        Producer<String, String> producer = new Producer<String, String>(new ProducerConfig(props));

        int messageNo = 100;
        final int COUNT = 1000;
        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            producer.send(new KeyedMessage<String, String>("TestTopic", key ,data));
            System.out.println(data);
            messageNo ++;
        }
    }  
}  