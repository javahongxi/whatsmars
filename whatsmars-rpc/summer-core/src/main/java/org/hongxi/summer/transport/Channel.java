package org.hongxi.summer.transport;

import org.hongxi.summer.rpc.Request;
import org.hongxi.summer.rpc.Response;
import org.hongxi.summer.rpc.URL;

import java.net.InetSocketAddress;

/**
 * Created by shenhongxi on 2020/6/14.
 */
public interface Channel {

    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();

    Response request(Request request) throws TransportException;

    boolean open();

    void close();

    void close(int timeout);

    boolean isClosed();

    boolean isAvailable();

    URL getUrl();
}
