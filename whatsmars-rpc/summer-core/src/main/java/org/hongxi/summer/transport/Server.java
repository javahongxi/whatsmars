package org.hongxi.summer.transport;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * Created by shenhongxi on 2020/6/25.
 */
public interface Server extends Endpoint {

    boolean isBound();

    Collection<Channel> getChannels();

    Channel getChannel(InetSocketAddress remoteAddress);
}
