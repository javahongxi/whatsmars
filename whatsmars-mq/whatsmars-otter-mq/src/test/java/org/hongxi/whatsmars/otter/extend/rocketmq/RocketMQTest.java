package org.hongxi.whatsmars.otter.extend.rocketmq;

import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;

/**
 * Created by shenhongxi on 2018/12/12.
 */
public class RocketMQTest {

    private static final String TEST_TOPIC = "sdk-test";

//    -Drocketmq.namesrv.addr=127.0.0.1:9876

    @Test
    public void send() {
        int count = 0;
        for (int i = 0; i < 20; i++) {
            try {
                SendResult sendResult = RocketMQTemplate.send(TEST_TOPIC, "hello" + i, 1000);
                System.out.println(sendResult);
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("successful: " + count);
    }

    @Test
    public void consume() throws Exception {
        TestConsumer testConsumer = new TestConsumer();
        testConsumer.startConsume("test-consumer", TEST_TOPIC);
        System.out.println("test-consumer started");
        Thread.sleep(30000);
    }
}
