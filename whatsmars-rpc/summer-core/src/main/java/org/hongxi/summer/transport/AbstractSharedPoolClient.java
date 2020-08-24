package org.hongxi.summer.transport;

import org.hongxi.summer.common.URLParamType;
import org.hongxi.summer.common.threadpool.DefaultThreadFactory;
import org.hongxi.summer.common.threadpool.StandardThreadPoolExecutor;
import org.hongxi.summer.common.util.CollectionUtils;
import org.hongxi.summer.common.util.MathUtils;
import org.hongxi.summer.exception.SummerServiceException;
import org.hongxi.summer.rpc.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shenhongxi on 2020/7/28.
 */
public abstract class AbstractSharedPoolClient extends AbstractClient {
    private static final Logger logger = LoggerFactory.getLogger(AbstractSharedPoolClient.class);

    private static final ThreadPoolExecutor EXECUTOR = new StandardThreadPoolExecutor(1, 300, 20000,
            new DefaultThreadFactory("AbstractPoolClient-initPool-", true));
    private final AtomicInteger idx = new AtomicInteger();
    protected SharedObjectFactory<Channel> factory;
    protected List<Channel> channels;
    protected int connections;

    public AbstractSharedPoolClient(URL url) {
        super(url);
        connections = url.getIntParameter(URLParamType.minClientConnections.getName(),
                URLParamType.minClientConnections.intValue());
        if (connections <= 0) {
            connections = URLParamType.minClientConnections.intValue();
        }
    }

    protected void initPool() {
        factory = createChannelFactory();

        channels = new ArrayList<>(connections);
        for (int i = 0; i < connections; i++) {
            channels.add(factory.makeObject());
        }

        initConnections(url.getBooleanParameter(URLParamType.asyncInitConnection.getName(),
                URLParamType.asyncInitConnection.boolValue()));
    }

    protected abstract SharedObjectFactory createChannelFactory();

    protected void initConnections(boolean async) {
        if (async) {
            EXECUTOR.execute(() -> createConnections());
        } else {
            createConnections();
        }
    }

    private void createConnections() {
        for (Channel channel : channels) {
            try {
                channel.open();
            } catch (Exception e) {
                logger.error("init pool create connect Error: url={}", url.getUri(), e);
            }
        }
    }

    protected Channel getChannel() {
        int index = MathUtils.getNonNegativeRange24bit(idx.getAndIncrement());
        Channel channel;

        for (int i = index; i < connections + 1 + index; i++) {
            channel = channels.get(i % connections);
            if (!channel.isAvailable()) {
                factory.rebuildObject(channel, i != connections + 1);
            }
            if (channel.isAvailable()) {
                return channel;
            }
        }

        String errorMsg = this.getClass().getSimpleName() + " getChannel Error: url=" + url.getUri();
        logger.error(errorMsg);
        throw new SummerServiceException(errorMsg);
    }

    protected void closeAllChannels() {
        if (!CollectionUtils.isEmpty(channels)) {
            for (Channel channel : channels) {
                channel.close();
            }
        }
    }
}
