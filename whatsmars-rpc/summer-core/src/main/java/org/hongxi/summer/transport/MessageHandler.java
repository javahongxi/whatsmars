package org.hongxi.summer.transport;

/**
 * Created by shenhongxi on 2020/6/27.
 */
public interface MessageHandler {

    Object handle(Channel channel, Object message);
}
