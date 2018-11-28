package org.hongxi.whatsmars.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by shenhongxi on 2017/9/8.
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("TEST.QUEUE");
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i= 0; i < 100; i++) {
            TextMessage message = session.createTextMessage("hello world! " + i);
            producer.send(message);
            System.out.println(message);
        }
        producer.close();
    }
}
