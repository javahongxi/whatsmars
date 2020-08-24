package org.hongxi.summer.transport;

import org.hongxi.summer.codec.Codec;
import org.hongxi.summer.common.ChannelState;
import org.hongxi.summer.common.URLParamType;
import org.hongxi.summer.common.extension.ExtensionLoader;
import org.hongxi.summer.common.util.SummerFrameworkUtils;
import org.hongxi.summer.exception.SummerFrameworkException;
import org.hongxi.summer.rpc.Request;
import org.hongxi.summer.rpc.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by shenhongxi on 2020/7/28.
 */
public abstract class AbstractClient implements Client {
    private static final Logger logger = LoggerFactory.getLogger(AbstractClient.class);

    protected InetSocketAddress localAddress;
    protected InetSocketAddress remoteAddress;

    protected URL url;
    protected Codec codec;

    protected volatile ChannelState state = ChannelState.UNINIT;

    public AbstractClient(URL url) {
        this.url = url;
        this.codec =
                ExtensionLoader.getExtensionLoader(Codec.class).getExtension(
                        url.getParameter(URLParamType.codec.getName(), URLParamType.codec.value()));
        logger.info("init netty client. url: " + url.getHost() + "-" + url.getPath() + ", use codec: " + codec.getClass().getSimpleName());
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public void heartbeat(Request request) {
        throw new SummerFrameworkException("heartbeat not support: " + SummerFrameworkUtils.toString(request));
    }

    public void setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }
}
