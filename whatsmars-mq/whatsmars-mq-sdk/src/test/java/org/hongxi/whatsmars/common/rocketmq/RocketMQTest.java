package org.hongxi.whatsmars.common.rocketmq;

import org.apache.rocketmq.common.MixAll;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by shenhongxi on 2018/12/12.
 */
public class RocketMQTest {

    private static final String TEST_TOPIC = "sdk-test";

    @Before
    public void namesrv() {
        System.setProperty(MixAll.NAMESRV_ADDR_PROPERTY, "127.0.0.1:9876");
    }

    @Test
    public void send() {
        int count = 0;
        for (int i = 0; i < 20; i++) {
            try {
                RocketTemplate.send(TEST_TOPIC, "hello" + i);
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("send successful: " + count);
    }

    @Test
    public void consume() throws Exception {
        TestConsumer testConsumer = new TestConsumer();
        testConsumer.startConsume("test-consumer", TEST_TOPIC);
        System.out.println("test-consumer started");
        Thread.sleep(30000);
    }
}
