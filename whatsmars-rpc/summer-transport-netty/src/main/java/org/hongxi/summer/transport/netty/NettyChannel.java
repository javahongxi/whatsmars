package org.hongxi.summer.transport.netty;

import io.netty.channel.ChannelFuture;
import org.hongxi.summer.CodecUtils;
import org.hongxi.summer.codec.Codec;
import org.hongxi.summer.common.ChannelState;
import org.hongxi.summer.common.URLParamType;
import org.hongxi.summer.common.extension.ExtensionLoader;
import org.hongxi.summer.common.util.ExceptionUtils;
import org.hongxi.summer.common.util.SummerFrameworkUtils;
import org.hongxi.summer.exception.SummerErrorMsgConstants;
import org.hongxi.summer.exception.SummerFrameworkException;
import org.hongxi.summer.exception.SummerServiceException;
import org.hongxi.summer.rpc.*;
import org.hongxi.summer.transport.Channel;
import org.hongxi.summer.transport.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shenhongxi on 2020/7/30.
 */
public class NettyChannel implements Channel {
    private static final Logger logger = LoggerFactory.getLogger(NettyChannel.class);

    private volatile ChannelState state = ChannelState.UNINIT;
    private NettyClient nettyClient;
    private io.netty.channel.Channel channel = null;
    private InetSocketAddress remoteAddress = null;
    private InetSocketAddress localAddress = null;
    private ReentrantLock lock = new ReentrantLock();
    private Codec codec;

    public NettyChannel(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        this.remoteAddress = new InetSocketAddress(nettyClient.getUrl().getHost(), nettyClient.getUrl().getPort());
        codec = ExtensionLoader.getExtensionLoader(Codec.class).getExtension(
                nettyClient.getUrl().getParameter(URLParamType.codec.getName(), URLParamType.codec.value()));
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
    public Response request(Request request) throws TransportException {
        int timeout = nettyClient.getUrl().getMethodParameter(
                request.getMethodName(), request.getParametersDesc(),
                URLParamType.requestTimeout.getName(), URLParamType.requestTimeout.intValue());
        if (timeout <= 0) {
            throw new SummerFrameworkException(
                    "NettyClient init Error: timeout(" + timeout + ") <= 0 is forbid.",
                    SummerErrorMsgConstants.FRAMEWORK_INIT_ERROR);
        }
        ResponseFuture response = new DefaultResponseFuture(request, timeout, this.nettyClient.getUrl());
        this.nettyClient.registerCallback(request.getRequestId(), response);
        byte[] msg = CodecUtils.encodeObjectToBytes(this, codec, request);
        ChannelFuture writeFuture = this.channel.writeAndFlush(msg);
        boolean result = writeFuture.awaitUninterruptibly(timeout, TimeUnit.MILLISECONDS);

        if (result && writeFuture.isSuccess()) {
            response.addListener(new FutureListener() {
                @Override
                public void operationComplete(Future future) throws Exception {
                    if (future.isSuccess() || (future.isDone() && ExceptionUtils.isBizException(future.getException()))) {
                        // 成功的调用
                        nettyClient.resetErrorCount();
                    } else {
                        // 失败的调用
                        nettyClient.incrErrorCount();
                    }
                }
            });
            return response;
        }

        writeFuture.cancel(true);
        response = this.nettyClient.removeCallback(request.getRequestId());
        if (response != null) {
            response.cancel();
        }
        // 失败的调用
        nettyClient.incrErrorCount();

        if (writeFuture.cause() != null) {
            throw new SummerServiceException("NettyChannel send request to server Error: url="
                    + nettyClient.getUrl().getUri() + " local=" + localAddress + " "
                    + SummerFrameworkUtils.toString(request), writeFuture.cause());
        } else {
            throw new SummerServiceException("NettyChannel send request to server Timeout: url="
                    + nettyClient.getUrl().getUri() + " local=" + localAddress + " "
                    + SummerFrameworkUtils.toString(request));
        }
    }

    @Override
    public synchronized boolean open() {
        if (isAvailable()) {
            logger.warn("the channel already open, local: {} remote: {} url: {}",
                    localAddress, remoteAddress, nettyClient.getUrl().getUri());
            return true;
        }

        ChannelFuture channelFuture = null;
        try {
            long start = System.currentTimeMillis();
            channelFuture = nettyClient.getBootstrap().connect(remoteAddress);
            int timeout = nettyClient.getUrl().getIntParameter(
                    URLParamType.connectTimeout.getName(), URLParamType.connectTimeout.intValue());
            if (timeout <= 0) {
                throw new SummerFrameworkException("NettyClient init Error: timeout(" + timeout + ") <= 0 is forbid.",
                        SummerErrorMsgConstants.FRAMEWORK_INIT_ERROR);
            }
            // 不去依赖于connectTimeout
            boolean result = channelFuture.awaitUninterruptibly(timeout, TimeUnit.MILLISECONDS);
            boolean success = channelFuture.isSuccess();

            if (result && success) {
                channel = channelFuture.channel();
                if (channel.localAddress() != null && channel.localAddress() instanceof InetSocketAddress) {
                    localAddress = (InetSocketAddress) channel.localAddress();
                }
                state = ChannelState.ALIVE;
                return true;
            }
            boolean connected = false;
            if (channelFuture.channel() != null) {
                connected = channelFuture.channel().isActive();
            }

            if (channelFuture.cause() != null) {
                channelFuture.cancel(true);
                throw new SummerServiceException(
                        "NettyChannel failed to connect to server, url: " +
                                nettyClient.getUrl().getUri() + ", result: " + result +
                                ", success: " + success +
                                ", connected: " + connected, channelFuture.cause());
            } else {
                channelFuture.cancel(true);
                throw new SummerServiceException("NettyChannel connect to server timeout url: " +
                        nettyClient.getUrl().getUri() +
                        ", cost: " + (System.currentTimeMillis() - start) +
                        ", result: " + result +
                        ", success: " + success +
                        ", connected: " + connected);
            }
        } catch (SummerServiceException e) {
            throw e;
        } catch (Exception e) {
            if (channelFuture != null) {
                channelFuture.channel().close();
            }
            throw new SummerServiceException("NettyChannel failed to connect to server, url: " +
                    nettyClient.getUrl().getUri(), e);
        } finally {
            if (!state.isAliveState()) {
                nettyClient.incrErrorCount();
            }
        }
    }

    @Override
    public synchronized void close() {
        close(0);
    }

    @Override
    public synchronized void close(int timeout) {
        try {
            state = ChannelState.CLOSE;

            if (channel != null) {
                channel.close();
            }
        } catch (Exception e) {
            logger.error("channel close Error: {} local={}",
                    nettyClient.getUrl().getUri(), localAddress, e);
        }
    }

    @Override
    public boolean isClosed() {
        return state.isCloseState();
    }

    @Override
    public boolean isAvailable() {
        return state.isAliveState() && channel != null && channel.isActive();
    }

    public void reconnect() {
        state = ChannelState.INIT;
    }

    public boolean isReconnect() {
        return state.isInitState();
    }

    @Override
    public URL getUrl() {
        return nettyClient.getUrl();
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
