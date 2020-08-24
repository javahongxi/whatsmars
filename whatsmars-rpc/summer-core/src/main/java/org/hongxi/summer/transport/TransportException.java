package org.hongxi.summer.transport;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by shenhongxi on 2020/6/14.
 */
public class TransportException extends IOException {
    private static final long serialVersionUID = -6184671228777275302L;

    private InetSocketAddress localAddress;
    private InetSocketAddress remoteAddress;

    public TransportException(String message, InetSocketAddress localAddress, InetSocketAddress remoteAddress) {
        super(message);
        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public TransportException(String message, Throwable cause, InetSocketAddress localAddress, InetSocketAddress remoteAddress) {
        super(message, cause);
        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }
}
