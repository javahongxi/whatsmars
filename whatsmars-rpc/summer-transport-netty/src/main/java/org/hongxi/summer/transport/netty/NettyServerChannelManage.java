package org.hongxi.summer.transport.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by shenhongxi on 2020/6/27.
 */
@ChannelHandler.Sharable
public class NettyServerChannelManage extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerChannelManage.class);

    private ConcurrentMap<String, Channel> channels = new ConcurrentHashMap<>();

    private int maxChannels;

    public NettyServerChannelManage(int maxChannels) {
        super();
        this.maxChannels = maxChannels;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if (channels.size() >= maxChannels) {
            logger.warn("connected channel size out of limit: limit={} current={}", maxChannels, channels.size());
            channel.close();
        } else {
            String channelKey = getChannelKey((InetSocketAddress) channel.localAddress(), (InetSocketAddress) channel.remoteAddress());
            channels.put(channelKey, channel);
            ctx.fireChannelRegistered();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String channelKey = getChannelKey((InetSocketAddress) channel.localAddress(), (InetSocketAddress) channel.remoteAddress());
        channels.remove(channelKey);
        ctx.fireChannelUnregistered();
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    private String getChannelKey(InetSocketAddress localAddress, InetSocketAddress remoteAddress) {
        String key = "";
        if (localAddress == null || localAddress.getAddress() == null) {
            key += "null-";
        } else {
            key += localAddress.getAddress().getHostAddress() + ":" + localAddress.getPort() + "-";
        }

        if (remoteAddress == null || remoteAddress.getAddress() == null) {
            key += "null";
        } else {
            key += remoteAddress.getAddress().getHostAddress() + ":" + remoteAddress.getPort();
        }
        return key;
    }

    public void close() {
        channels.forEach((k, v) -> {
            if (v != null) {
                try {
                    v.close();
                } catch (Exception e) {
                    logger.error("close channel error, {}", k, e);
                }
            }
        });
    }
}
