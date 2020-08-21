package org.hongxi.whatsmars.netty.nio;

/**
 * Created by jjenkov on 16-10-2015.
 */
public interface MessageProcessor {

    void process(Message message, WriteProxy writeProxy);

}
