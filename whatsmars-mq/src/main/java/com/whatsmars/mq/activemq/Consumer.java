package com.whatsmars.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by shenhongxi on 2017/9/8.
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("TEST.QUEUE");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message);
            }
        });

        connection.start();
    }
}
