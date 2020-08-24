package org.hongxi.summer.transport.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.hongxi.summer.common.ChannelState;
import org.hongxi.summer.common.SummerConstants;
import org.hongxi.summer.common.URLParamType;
import org.hongxi.summer.common.util.SummerFrameworkUtils;
import org.hongxi.summer.exception.SummerAbstractException;
import org.hongxi.summer.exception.SummerErrorMsgConstants;
import org.hongxi.summer.exception.SummerFrameworkException;
import org.hongxi.summer.exception.SummerServiceException;
import org.hongxi.summer.rpc.*;
import org.hongxi.summer.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by shenhongxi on 2020/7/28.
 */
public class NettyClient extends AbstractSharedPoolClient {
    private static final Logger logger = LoggerFactory.getLogger(AbstractSharedPoolClient.class);

    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
    /**
     * 回收过期任务
     */
    private static ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    /**
     * 异步的request，需要注册callback future
     * 触发remove的操作有： 1) service的返回结果处理。 2) timeout thread cancel
     */
    protected ConcurrentMap<Long, ResponseFuture> callbackMap = new ConcurrentHashMap<>();
    private ScheduledFuture<?> timeMonitorFuture;
    private Bootstrap bootstrap;
    private int fusingThreshold;
    /**
     * 连续失败次数
     */
    private AtomicLong errorCount = new AtomicLong(0);

    public NettyClient(URL url) {
        super(url);
        fusingThreshold = url.getIntParameter(URLParamType.fusingThreshold.getName(),
                URLParamType.fusingThreshold.intValue());
        timeMonitorFuture = scheduledExecutor.scheduleWithFixedDelay(
                new TimeoutMonitor("timeout_monitor_" + url.getHost() + "_" + url.getPort()),
                SummerConstants.NETTY_TIMEOUT_TIMER_PERIOD, SummerConstants.NETTY_TIMEOUT_TIMER_PERIOD,
                TimeUnit.MILLISECONDS);
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    @Override
    protected SharedObjectFactory createChannelFactory() {
        return new NettyChannelFactory(this);
    }

    @Override
    public Response request(Request request) throws TransportException {
        if (!isAvailable()) {
            throw new SummerServiceException("NettyChannel is unavailable: url="
                    + url.getUri() + SummerFrameworkUtils.toString(request));
        }
        boolean isAsync = false;
        Object async = RpcContext.getContext().getAttribute(SummerConstants.ASYNC_SUFFIX);
        if (async != null && async instanceof Boolean) {
            isAsync = (Boolean) async;
        }
        return request(request, isAsync);
    }

    private Response request(Request request, boolean async) throws TransportException {
        Channel channel;
        Response response;
        try {
            // return channel or throw exception(timeout or connection_fail)
            channel = getChannel();

            if (channel == null) {
                logger.error("borrowObject null: url={} {}", url.getUri(),
                        SummerFrameworkUtils.toString(request));
                return null;
            }

            // async request
            response = channel.request(request);
        } catch (Exception e) {
            logger.error("request Error: url={} {}, {}", url.getUri(),
                    SummerFrameworkUtils.toString(request), e.getMessage());

            if (e instanceof SummerAbstractException) {
                throw (SummerAbstractException) e;
            } else {
                throw new SummerServiceException("NettyClient request Error: url=" +
                        url.getUri() + " " + SummerFrameworkUtils.toString(request), e);
            }
        }

        // aysnc or sync result
        response = asyncResponse(response, async);

        return response;
    }

    /**
     * 如果async是false，那么同步获取response的数据
     *
     * @param response
     * @param async
     * @return
     */
    private Response asyncResponse(Response response, boolean async) {
        if (async || !(response instanceof ResponseFuture)) {
            return response;
        }
        return new DefaultResponse(response);
    }

    @Override
    public synchronized boolean open() {
        if (isAvailable()) {
            return true;
        }

        bootstrap = new Bootstrap();
        int timeout = getUrl().getIntParameter(URLParamType.connectTimeout.getName(),
                URLParamType.connectTimeout.intValue());
        if (timeout <= 0) {
            throw new SummerFrameworkException("NettyClient init Error: timeout(" +
                    timeout + ") <= 0 is forbid.",
                    SummerErrorMsgConstants.FRAMEWORK_INIT_ERROR);
        }
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // 最大响应包限制
        final int maxContentLength = url.getIntParameter(URLParamType.maxContentLength.getName(),
                URLParamType.maxContentLength.intValue());
        bootstrap.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new NettyDecoder(codec, NettyClient.this, maxContentLength));
                        pipeline.addLast("encoder", new NettyEncoder());
                        pipeline.addLast("handler", new NettyChannelHandler(NettyClient.this, new MessageHandler() {
                            @Override
                            public Object handle(Channel channel, Object message) {
                                Response response = (Response) message;
                                ResponseFuture responseFuture = NettyClient.this.removeCallback(response.getRequestId());

                                if (responseFuture == null) {
                                    logger.warn("has response from server, but responseFuture not exist, requestId={}",
                                            response.getRequestId());
                                    return null;
                                }
                                if (response.getException() != null) {
                                    responseFuture.onFailure(response);
                                } else {
                                    responseFuture.onSuccess(response);
                                }
                                return null;
                            }
                        }));
                    }
                });

        // 初始化连接池
        initPool();

        logger.info("NettyClient finished open: url={}", url);

        // 设置可用状态
        state = ChannelState.ALIVE;
        return true;
    }

    @Override
    public synchronized void close() {
        close(0);
    }

    @Override
    public synchronized void close(int timeout) {
        if (state.isCloseState()) {
            return;
        }

        try {
            cleanup();
            if (state.isUnInitState()) {
                logger.info("NettyClient close failed: state={}, url={}", state.value(), url.getUri());
                return;
            }

            // 设置close状态
            state = ChannelState.CLOSE;
            logger.info("NettyClient close Success: url={}", url.getUri());
        } catch (Exception e) {
            logger.error("NettyClient close Error: url={}", url.getUri(), e);
        }
    }

    public void cleanup() {
        // 取消定期的回收任务
        timeMonitorFuture.cancel(true);
        // 清空callback
        callbackMap.clear();
        // 关闭client持有的channel
        closeAllChannels();
    }

    @Override
    public boolean isClosed() {
        return state.isCloseState();
    }

    @Override
    public boolean isAvailable() {
        return state.isAliveState();
    }

    @Override
    public URL getUrl() {
        return url;
    }

    public ResponseFuture removeCallback(long requestId) {
        return callbackMap.remove(requestId);
    }

    /**
     * 增加调用失败的次数：
     * <p>
     * <pre>
     * 	 	如果连续失败的次数 >= maxClientConnection, 那么把client设置成不可用状态
     * </pre>
     */
    void incrErrorCount() {
        long count = errorCount.incrementAndGet();

        // 如果节点是可用状态，同时当前连续失败的次数超过熔断阈值，那么把该节点标示为不可用
        if (count >= fusingThreshold && state.isAliveState()) {
            synchronized (this) {
                count = errorCount.longValue();

                if (count >= fusingThreshold && state.isAliveState()) {
                    logger.error("NettyClient unavailable Error: url={} {}",
                            url.getIdentity(), url.getServerPortStr());
                    state = ChannelState.UNALIVE;
                }
            }
        }
    }

    /**
     * 重置调用失败的计数 ：
     * <pre>
     * 把节点设置成可用
     * </pre>
     */
    void resetErrorCount() {
        errorCount.set(0);

        if (state.isAliveState()) {
            return;
        }

        synchronized (this) {
            if (state.isAliveState()) {
                return;
            }

            // 如果节点是unalive才进行设置，而如果是 close 或者 uninit，那么直接忽略
            if (state.isUnAliveState()) {
                long count = errorCount.longValue();

                // 过程中有其他并发更新errorCount的，因此这里需要进行一次判断
                if (count < fusingThreshold) {
                    state = ChannelState.ALIVE;
                    logger.info("NettyClient recover available: url={} {}",
                            url.getIdentity(), url.getServerPortStr());
                }
            }
        }
    }

    /**
     * 注册回调的resposne
     * <pre>
     * 进行最大的请求并发数的控制，如果超过NETTY_CLIENT_MAX_REQUEST的话，那么throw reject exception
     * </pre>
     *
     * @param requestId
     * @param responseFuture
     * @throws SummerServiceException
     */
    public void registerCallback(long requestId, ResponseFuture responseFuture) {
        if (this.callbackMap.size() >= SummerConstants.NETTY_CLIENT_MAX_REQUEST) {
            // reject request, prevent from OutOfMemoryError
            throw new SummerServiceException("NettyClient over of max concurrent request, drop request, url: "
                    + url.getUri() + " requestId=" + requestId, SummerErrorMsgConstants.SERVICE_REJECT);
        }

        this.callbackMap.put(requestId, responseFuture);
    }

    /**
     * 回收超时任务
     */
    class TimeoutMonitor implements Runnable {
        private String name;

        public TimeoutMonitor(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            for (Map.Entry<Long, ResponseFuture> entry : callbackMap.entrySet()) {
                try {
                    ResponseFuture future = entry.getValue();

                    if (future.getCreateTime() + future.getTimeout() < currentTime) {
                        // timeout: remove from callback list, and then cancel
                        removeCallback(entry.getKey());
                        future.cancel();
                    }
                } catch (Exception e) {
                    logger.error("{} clear timeout future Error: uri={} requestId={}",
                            name, url.getUri(), entry.getKey(), e);
                }
            }
        }
    }
}
