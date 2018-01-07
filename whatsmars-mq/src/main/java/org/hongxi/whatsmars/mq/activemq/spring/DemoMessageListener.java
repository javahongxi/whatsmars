package org.hongxi.whatsmars.mq.activemq.spring;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * Created by liuguanqing on 15/5/18.
 */
@Service
public class DemoMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message;

        try {
            System.out.println(textMessage.getText());
            //......
            //执行成功后，确认消息
            message.acknowledge();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
