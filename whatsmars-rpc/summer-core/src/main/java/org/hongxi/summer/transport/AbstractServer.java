package org.hongxi.summer.transport;

import org.hongxi.summer.codec.Codec;
import org.hongxi.summer.common.ChannelState;
import org.hongxi.summer.common.URLParamType;
import org.hongxi.summer.common.extension.ExtensionLoader;
import org.hongxi.summer.exception.SummerFrameworkException;
import org.hongxi.summer.rpc.URL;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * Created by shenhongxi on 2020/6/25.
 */
public abstract class AbstractServer implements Server {
    protected InetSocketAddress localAddress;
    protected InetSocketAddress remoteAddress;

    protected URL url;
    protected Codec codec;

    protected volatile ChannelState state = ChannelState.UNINIT;

    public AbstractServer() {}

    public AbstractServer(URL url) {
        this.url = url;
        this.codec = ExtensionLoader.getExtensionLoader(Codec.class).getExtension(
                url.getParameter(URLParamType.codec.getName(), URLParamType.codec.value()));
    }

    @Override
    public Collection<Channel> getChannels() {
        throw new SummerFrameworkException(this.getClass().getName() + " getChannels() method not support " + url);
    }

    @Override
    public Channel getChannel(InetSocketAddress remoteAddress) {
        throw new SummerFrameworkException(this.getClass().getName() + " getChannels(InetSocketAddress) method not support " + url);
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }
}
