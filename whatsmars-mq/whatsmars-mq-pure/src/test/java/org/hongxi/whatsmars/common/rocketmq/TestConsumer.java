package org.hongxi.whatsmars.common.rocketmq;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * Created by shenhongxi on 2018/12/12.
 */
public class TestConsumer extends BaseConsumer {
    @Override
    protected void process(MessageExt messageExt) {
        System.out.println(new String(messageExt.getBody()));
    }
}
