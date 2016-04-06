package com.whatsmars.support.web.service.listener;

import net.sf.json.JSONObject;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by liuguanqing on 15/5/18.
 */
@Service
public class TestListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message;

        try {
            JSONObject json = JSONObject.fromObject(textMessage.getText());
            if(!json.has("flowId")) {
                return;
            }
            int flowId = json.getInt("flowId");
            String from = json.getString("from");
            //......
            //执行成功后，确认消息
            message.acknowledge();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws Exception{

        sender();
        Thread.sleep(3000);
        receive();

    }


    public static void sender() throws Exception{
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://61.172.238.149:61616");
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("_XHD_MQ_BIDDING.QUEUE");
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.send(session.createTextMessage("hello world!"));
        producer.close();
    }

    public static void receive() throws Exception {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://61.172.238.149:61616");
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("_XHD_MQ_RECHARGE.QUEUE");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message.toString());
            }
        });

        connection.start();
    }
}
