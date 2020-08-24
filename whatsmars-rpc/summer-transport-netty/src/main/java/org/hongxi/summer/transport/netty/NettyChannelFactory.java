package org.hongxi.summer.transport.netty;

import org.hongxi.summer.common.threadpool.DefaultThreadFactory;
import org.hongxi.summer.common.threadpool.StandardThreadPoolExecutor;
import org.hongxi.summer.transport.SharedObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by shenhongxi on 2020/7/30.
 */
public class NettyChannelFactory implements SharedObjectFactory<NettyChannel> {
    private static final Logger logger = LoggerFactory.getLogger(NettyChannelFactory.class);

    private static final ExecutorService rebuildExecutorService = new StandardThreadPoolExecutor(
            5, 30, 10L, TimeUnit.SECONDS, 100,
            new DefaultThreadFactory("RebuildExecutorService", true),
            new ThreadPoolExecutor.CallerRunsPolicy());
    private NettyClient nettyClient;
    private String factoryName;

    public NettyChannelFactory(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        this.factoryName = "NettyChannelFactory_" + nettyClient.getUrl().getHost() +
                "_" + nettyClient.getUrl().getPort();
    }

    @Override
    public NettyChannel makeObject() {
        return new NettyChannel(nettyClient);
    }

    @Override
    public boolean rebuildObject(NettyChannel nettyChannel, boolean async) {
        ReentrantLock lock = nettyChannel.getLock();
        if (lock.tryLock()) {
            try {
                if (!nettyChannel.isAvailable() && !nettyChannel.isReconnect()) {
                    nettyChannel.reconnect();
                    if (async) {
                        rebuildExecutorService.submit(new RebuildTask(nettyChannel));
                    } else {
                        nettyChannel.close();
                        nettyChannel.open();
                        logger.info("rebuild channel success: {}", nettyChannel.getUrl());
                    }
                }
            } catch (Exception e) {
                logger.error("rebuild error: {}, {}", this.toString(), nettyChannel.getUrl(), e);
            } finally {
                lock.unlock();
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return factoryName;
    }

    class RebuildTask implements Runnable {
        private NettyChannel channel;

        public RebuildTask(NettyChannel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            try {
                channel.getLock().lock();
                channel.close();
                channel.open();
                logger.info("rebuild channel success: {}", channel.getUrl());
            } catch (Exception e) {
                logger.error("rebuild error: {}, {}", this.toString(), channel.getUrl(), e);
            } finally {
                channel.getLock().unlock();
            }
        }
    }
}
