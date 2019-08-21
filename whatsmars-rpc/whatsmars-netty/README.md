### Netty线程模型
Netty的代码很抽象，适合debug和UML跟

RocketMQ `NettyRemotingServer`
```java
    ServerBootstrap childHandler =
            // EventLoopGroup: bossGroup, workerGroup
            this.serverBootstrap.group(this.eventLoopGroupBoss, this.eventLoopGroupSelector)
                .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize())
                .childOption(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize())
                .localAddress(new InetSocketAddress(this.nettyServerConfig.getListenPort()))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                            // DefaultEventExecutorGroup: optionalGroup (相当于netty提供的业务线程池)
                            .addLast(defaultEventExecutorGroup, HANDSHAKE_HANDLER_NAME,
                                new HandshakeHandler(TlsSystemConfig.tlsMode))
                            .addLast(defaultEventExecutorGroup,
                                new NettyEncoder(),
                                new NettyDecoder(),
                                new IdleStateHandler(0, 0, nettyServerConfig.getServerChannelMaxIdleTimeSeconds()),
                                new NettyConnectManageHandler(),
                                // NettyServerHandler里有业务处理线程池
                                new NettyServerHandler()
                            );
                    }
                });
```

bossGroup,workerGroup,optionalGroup实质都用的是 `MultithreadEventExecutorGroup`, 其维护的nThreads个executor都是`ThreadPerTaskExecutor`
```java
protected MultithreadEventExecutorGroup(int nThreads, Executor executor, Object... args) {
    if (nThreads <= 0) {
        throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", nThreads));
    }
 		
    // 使用者没传threadFactory时，executor = null
    // 传了时，executor = new ThreadPerTaskExecutor(threadFactory)
    if (executor == null) {
        executor = new ThreadPerTaskExecutor(newDefaultThreadFactory());
    }
 
    children = new EventExecutor[nThreads];
    for (int i = 0; i < nThreads; i ++) {
        boolean success = false;
        try {
            children[i] = newChild(executor, args);
            success = true;
        } catch (Exception e) {
            // TODO: Think about if this is a good exception type
            throw new IllegalStateException("failed to create a child event loop", e);
        } finally {
            // ...
        }
    }
}
```

![img](https://github.com/javahongxi/static/blob/master/netty_03.png)

![img](https://github.com/javahongxi/static/blob/master/netty_02.png)

`AbstractBootstrap` (main线程)
```java
    // serverBootstrap.bind()
    private ChannelFuture doBind(final SocketAddress localAddress) {
        final ChannelFuture regFuture = initAndRegister(); // 接下来看这里
        final Channel channel = regFuture.channel();
        if (regFuture.cause() != null) {
            return regFuture;
        }

        if (regFuture.isDone()) {
            // At this point we know that the registration was complete and successful.
            ChannelPromise promise = channel.newPromise();
            doBind0(regFuture, channel, localAddress, promise);
            return promise;
        } else {
            // ...
        }
    }

    final ChannelFuture initAndRegister() {
        Channel channel = null;
        try {
            channel = channelFactory.newChannel();
            init(channel); // 接下来看这里
        } catch (Throwable t) {
            // ...
        }

        ChannelFuture regFuture = config().group().register(channel); // 看完init看这里
        if (regFuture.cause() != null) {
            if (channel.isRegistered()) {
                channel.close();
            } else {
                channel.unsafe().closeForcibly();
            }
        }

        return regFuture;
    }
```

`ServerBootstrap` (main线程)
```java
    @Override
    void init(Channel channel) throws Exception {
        // ...

        ChannelPipeline p = channel.pipeline();

        final EventLoopGroup currentChildGroup = childGroup;
        final ChannelHandler currentChildHandler = childHandler;
        final Entry<ChannelOption<?>, Object>[] currentChildOptions;
        final Entry<AttributeKey<?>, Object>[] currentChildAttrs;
        synchronized (childOptions) {
            currentChildOptions = childOptions.entrySet().toArray(newOptionArray(0));
        }
        synchronized (childAttrs) {
            currentChildAttrs = childAttrs.entrySet().toArray(newAttrArray(0));
        }
        // ChannelInitializer的特点是执行initChannel后会随即从pipeline删除
        p.addLast(new ChannelInitializer<Channel>() {
            @Override
            public void initChannel(final Channel ch) throws Exception {
                final ChannelPipeline pipeline = ch.pipeline();
                ChannelHandler handler = config.handler();
                if (handler != null) {
                    pipeline.addLast(handler);
                }
                // 服务端channel的eventLoop去执行如下task，接下来我们关注eventLoop.execute(Runnable task)是怎么执行的
                ch.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        pipeline.addLast(new ServerBootstrapAcceptor(
                                ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
                    }
                });
            }
        });
    }
```

`NioEventLoop`
```java
    /**
     * The NIO {@link Selector}.
     */
    private Selector selector;
    private Selector unwrappedSelector;
    private SelectedSelectionKeySet selectedKeys;

    private final SelectorProvider provider;
```

`SingleThreadEventExecutor`
```java
    private final Queue<Runnable> taskQueue;

    private volatile Thread thread;
    
    private final Executor executor; // ThreadPerTaskExecutor

    @Override
    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException("task");
        }

        // this.thread == Thread.currentThread()
        // 第一次为false，因为刚开始thread为null
        boolean inEventLoop = inEventLoop();
        addTask(task); // taskQueue
        if (!inEventLoop) {
            startThread(); // 接下来看这个
            if (isShutdown() && removeTask(task)) {
                reject();
            }
        }

        if (!addTaskWakesUp && wakesUpForTask(task)) {
            wakeup(inEventLoop);
        }
    }
```

```java
    private void doStartThread() {
        assert thread == null;
        // ThreadPerTaskExecutor
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Thread.currentThread() == ThreadPerTaskExecutor创建的线程
                // 从这里我们知道，最初创建的NioEventLoop都是没有绑定线程的
                // 是在执行任务时由ThreadPerTaskExecutor创建线程赋给thread
                // thread有值后，下次判断inEventLoop()就可能为true了(视具体线程环境)
                thread = Thread.currentThread();
                if (interrupted) {
                    thread.interrupt();
                }

                boolean success = false;
                updateLastExecutionTime();
                try {
                    SingleThreadEventExecutor.this.run(); // 接下来看这个
                    success = true;
                } catch (Throwable t) {
                    logger.warn("Unexpected exception from an event executor: ", t);
                } finally {
                    // ...
                }
            }
        });
    }
```

`NioEventLoop`
```java
    @Override
    protected void run() {
        for (;;) {
            try {
                // 我们以为是select()，其实可能是selectNow()，前者阻塞，后者非阻塞
                switch (selectStrategy.calculateStrategy(selectNowSupplier, hasTasks())) {
                    case SelectStrategy.CONTINUE: // -2
                        continue;

                    case SelectStrategy.BUSY_WAIT: // -3
                        // fall-through to SELECT since the busy-wait is not supported with NIO

                    case SelectStrategy.SELECT: // -1
                        select(wakenUp.getAndSet(false));

                        if (wakenUp.get()) {
                            selector.wakeup();
                        }
                    default:
                }
                // 当hasTasks()为true时，以上case都不满足，直接走到这里
                cancelledKeys = 0;
                needsToSelectAgain = false;
                final int ioRatio = this.ioRatio;
                if (ioRatio == 100) {
                    try {
                        processSelectedKeys();
                    } finally {
                        // Ensure we always run tasks.
                        runAllTasks();
                    }
                } else { // 默认
                    final long ioStartTime = System.nanoTime();
                    try {
                        processSelectedKeys(); // 接下来看这里
                    } finally {
                        // Ensure we always run tasks.
                        final long ioTime = System.nanoTime() - ioStartTime;
                        runAllTasks(ioTime * (100 - ioRatio) / ioRatio); // 接下来看这里
                    }
                }
            } catch (Throwable t) {
                handleLoopException(t);
            }
            // ...
        }
    }
```

`DefaultSelectStrategy`
```java
    @Override
    public int calculateStrategy(IntSupplier selectSupplier, boolean hasTasks) throws Exception {
        return hasTasks ? selectSupplier.get() : SelectStrategy.SELECT; // 显然有task时返回值 >= 0
    }
```

`NioEventLoop`
```java
    private final IntSupplier selectNowSupplier = new IntSupplier() {
        @Override
        public int get() throws Exception {
            return selectNow(); // >= 0
        }
    };

    private void processSelectedKeysOptimized() {
        for (int i = 0; i < selectedKeys.size; ++i) {
            final SelectionKey k = selectedKeys.keys[i];
            // null out entry in the array to allow to have it GC'ed once the Channel close
            // See https://github.com/netty/netty/issues/2363
            selectedKeys.keys[i] = null;
            // channel注册selector时传了attachment
            final Object a = k.attachment();

            if (a instanceof AbstractNioChannel) {
                processSelectedKey(k, (AbstractNioChannel) a); // 接下来看这里
            } else {
                @SuppressWarnings("unchecked")
                NioTask<SelectableChannel> task = (NioTask<SelectableChannel>) a;
                processSelectedKey(k, task); // 接下来看这里
            }

            if (needsToSelectAgain) {
                // null out entries in the array to allow to have it GC'ed once the Channel close
                // See https://github.com/netty/netty/issues/2363
                selectedKeys.reset(i + 1);

                selectAgain();
                i = -1;
            }
        }
    }

    private void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {
        final AbstractNioChannel.NioUnsafe unsafe = ch.unsafe();
        if (!k.isValid()) {
            final EventLoop eventLoop;
            try {
                eventLoop = ch.eventLoop();
            } catch (Throwable ignored) {
                return;
            }
            if (eventLoop != this || eventLoop == null) {
                return;
            }
            unsafe.close(unsafe.voidPromise());
            return;
        }

        try {
            int readyOps = k.readyOps();
            // We first need to call finishConnect() before try to trigger a read(...) or write(...) as otherwise
            // the NIO JDK channel implementation may throw a NotYetConnectedException.
            if ((readyOps & SelectionKey.OP_CONNECT) != 0) {
                // remove OP_CONNECT as otherwise Selector.select(..) will always return without blocking
                // See https://github.com/netty/netty/issues/924
                int ops = k.interestOps();
                ops &= ~SelectionKey.OP_CONNECT;
                k.interestOps(ops);

                unsafe.finishConnect();
            }

            // Process OP_WRITE first as we may be able to write some queued buffers and so free memory.
            if ((readyOps & SelectionKey.OP_WRITE) != 0) {
                // Call forceFlush which will also take care of clear the OP_WRITE once there is nothing left to write
                ch.unsafe().forceFlush();
            }

            // Also check for readOps of 0 to workaround possible JDK bug which may otherwise lead
            // to a spin loop
            if ((readyOps & (SelectionKey.OP_READ | SelectionKey.OP_ACCEPT)) != 0 || readyOps == 0) {
                unsafe.read(); // 接下来看这里
            }
        } catch (CancelledKeyException ignored) {
            unsafe.close(unsafe.voidPromise());
        }
    }

    private static void processSelectedKey(SelectionKey k, NioTask<SelectableChannel> task) {
        int state = 0;
        try {
            task.channelReady(k.channel(), k); // 接下来看这里
            state = 1;
        } catch (Exception e) {
            k.cancel();
            invokeChannelUnregistered(task, k, e);
            state = 2;
        } finally {
            switch (state) {
            case 0:
                k.cancel();
                invokeChannelUnregistered(task, k, null);
                break;
            case 1:
                if (!k.isValid()) { // Cancelled by channelReady()
                    invokeChannelUnregistered(task, k, null);
                }
                break;
            }
        }
    }
```

`SingleThreadEventExecutor`
```java
    /**
     * Poll all tasks from the task queue and run them via {@link Runnable#run()} method.  This method stops running
     * the tasks in the task queue and returns if it ran longer than {@code timeoutNanos}.
     */
    protected boolean runAllTasks(long timeoutNanos) {
        fetchFromScheduledTaskQueue(); // scheduledTask会转移到taskQueue
        Runnable task = pollTask();
        if (task == null) {
            afterRunningAllTasks();
            return false;
        }

        final long deadline = ScheduledFutureTask.nanoTime() + timeoutNanos;
        long runTasks = 0;
        long lastExecutionTime;
        for (;;) {
            safeExecute(task); // Try to execute the given {@link Runnable} and just log if it throws a {@link Throwable}.

            runTasks ++;

            // Check timeout every 64 tasks because nanoTime() is relatively expensive.
            // XXX: Hard-coded value - will make it configurable if it is really a problem.
            if ((runTasks & 0x3F) == 0) {
                lastExecutionTime = ScheduledFutureTask.nanoTime();
                if (lastExecutionTime >= deadline) {
                    break;
                }
            }

            task = pollTask();
            if (task == null) {
                lastExecutionTime = ScheduledFutureTask.nanoTime();
                break;
            }
        }

        afterRunningAllTasks();
        this.lastExecutionTime = lastExecutionTime;
        return true;
    }
```

`AbstractNioMessageChannel`
```java
    private final class NioMessageUnsafe extends AbstractNioUnsafe {

        private final List<Object> readBuf = new ArrayList<Object>();

        @Override
        public void read() {
            assert eventLoop().inEventLoop();
            final ChannelConfig config = config();
            final ChannelPipeline pipeline = pipeline();
            final RecvByteBufAllocator.Handle allocHandle = unsafe().recvBufAllocHandle();
            allocHandle.reset(config);

            boolean closed = false;
            Throwable exception = null;
            try {
                try {
                    do {
                        int localRead = doReadMessages(readBuf); // 接下来看这里
                        if (localRead == 0) {
                            break;
                        }
                        if (localRead < 0) {
                            closed = true;
                            break;
                        }

                        allocHandle.incMessagesRead(localRead);
                    } while (allocHandle.continueReading()); // 控制连接接入速率
                } catch (Throwable t) {
                    exception = t;
                }

                int size = readBuf.size();
                for (int i = 0; i < size; i ++) {
                    readPending = false;
                    // 此处的pipeline是服务端channel的
                    // 传播channelRead事件触发acceptorHandler的channelRead方法
                    pipeline.fireChannelRead(readBuf.get(i));
                }
                readBuf.clear();
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();

                if (exception != null) {
                    closed = closeOnReadError(exception);

                    pipeline.fireExceptionCaught(exception);
                }

                if (closed) {
                    inputShutdown = true;
                    if (isOpen()) {
                        close(voidPromise());
                    }
                }
            } finally {
                // Check if there is a readPending which was not processed yet.
                // This could be for two reasons:
                // * The user called Channel.read() or ChannelHandlerContext.read() in channelRead(...) method
                // * The user called Channel.read() or ChannelHandlerContext.read() in channelReadComplete(...) method
                //
                // See https://github.com/netty/netty/issues/2254
                if (!readPending && !config.isAutoRead()) {
                    removeReadOp();
                }
            }
        }
    }
```

`NioServerSocketChannel`
```java
    @Override
    protected int doReadMessages(List<Object> buf) throws Exception {
        SocketChannel ch = SocketUtils.accept(javaChannel()); // 终于找到你

        try {
            if (ch != null) {
                buf.add(new NioSocketChannel(this, ch)); // 构造NioSocketChannel
                return 1;
            }
        } catch (Throwable t) {
            // ...
        }

        return 0;
    }
```

`ServerBootstrapAcceptor`
```java
        @Override
        @SuppressWarnings("unchecked")
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            final Channel child = (Channel) msg; // 客户端channel

            child.pipeline().addLast(childHandler); // ChannelInitializer

            setChannelOptions(child, childOptions, logger); // TCP参数

            for (Entry<AttributeKey<?>, Object> e: childAttrs) { // 自定义属性
                child.attr((AttributeKey<Object>) e.getKey()).set(e.getValue());
            }

            try {
                // 将客户端channel注册到workerGroup上
                childGroup.register(child).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            forceClose(child, future.cause());
                        }
                    }
                });
            } catch (Throwable t) {
                forceClose(child, t);
            }
        }
```

`SingleThreadEventLoop`
```java
    @Override
    public ChannelFuture register(Channel channel) {
        return register(new DefaultChannelPromise(channel, this));
    }

    @Override
    public ChannelFuture register(final ChannelPromise promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        promise.channel().unsafe().register(this, promise); // 接下来看这里
        return promise;
    }
```

`AbstractChannel`
```java
        @Override
        public final void register(EventLoop eventLoop, final ChannelPromise promise) {
            AbstractChannel.this.eventLoop = eventLoop;

            if (eventLoop.inEventLoop()) {
                register0(promise);
            } else {
                try {
                    eventLoop.execute(new Runnable() {
                        @Override
                        public void run() {
                            register0(promise); // 接下来看这里
                        }
                    });
                } catch (Throwable t) {
                    // ...
                }
            }
        }

        private void register0(ChannelPromise promise) {
            try {
                // check if the channel is still open as it could be closed in the mean time when the register
                // call was outside of the eventLoop
                if (!promise.setUncancellable() || !ensureOpen(promise)) {
                    return;
                }
                boolean firstRegistration = neverRegistered;
                doRegister(); // 接下来看这里
                neverRegistered = false;
                registered = true;

                // Ensure we call handlerAdded(...) before we actually notify the promise. This is needed as the
                // user may already fire events through the pipeline in the ChannelFutureListener.
                pipeline.invokeHandlerAddedIfNeeded();

                safeSetSuccess(promise);
                pipeline.fireChannelRegistered();
                // Only fire a channelActive if the channel has never been registered. This prevents firing
                // multiple channel actives if the channel is deregistered and re-registered.
                if (isActive()) {
                    if (firstRegistration) {
                        pipeline.fireChannelActive(); // 接下来看这里
                    } else if (config().isAutoRead()) {
                        // This channel was registered before and autoRead() is set. This means we need to begin read
                        // again so that we process inbound data.
                        //
                        // See https://github.com/netty/netty/issues/4805
                        beginRead();
                    }
                }
            } catch (Throwable t) {
                // Close the channel directly to avoid FD leak.
                closeForcibly();
                closeFuture.setClosed();
                safeSetFailure(promise, t);
            }
        }
```

`AbstractNioChannel`
```java
    @Override
    protected void doRegister() throws Exception {
        boolean selected = false;
        for (;;) {
            try {
                selectionKey = javaChannel().register(eventLoop().unwrappedSelector(), 0, this); // 终于见到你
                return;
            } catch (CancelledKeyException e) {
                if (!selected) {
                    // Force the Selector to select now as the "canceled" SelectionKey may still be
                    // cached and not removed because no Select.select(..) operation was called yet.
                    eventLoop().selectNow();
                    selected = true;
                } else {
                    // We forced a select operation on the selector before but the SelectionKey is still cached
                    // for whatever reason. JDK bug ?
                    throw e;
                }
            }
        }
    }
```

`DefaultChannelPipeline`
```java
    @Override
    public final ChannelPipeline fireChannelActive() {
        AbstractChannelHandlerContext.invokeChannelActive(head);
        return this;
    }
```

`AbstractChannelHandlerContext`
```java
    static void invokeChannelActive(final AbstractChannelHandlerContext next) {
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            next.invokeChannelActive(); // 接下来开这里
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    next.invokeChannelActive();
                }
            });
        }
    }

    private void invokeChannelActive() {
        if (invokeHandler()) {
            try {
                ((ChannelInboundHandler) handler()).channelActive(this); // HeadContext#channelActive
            } catch (Throwable t) {
                notifyHandlerException(t);
            }
        } else {
            fireChannelActive();
        }
    }
```

`HeadContext`
```java
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.fireChannelActive();

            readIfIsAutoRead(); // 看这里
        }

        private void readIfIsAutoRead() {
            if (channel.config().isAutoRead()) {
                channel.read(); // 看这里
            }
        }

        @Override
        public void read(ChannelHandlerContext ctx) {
            unsafe.beginRead(); // 看这里
        }
```

`AbstractUnsafe`
```java
        @Override
        public final void beginRead() {
            assertEventLoop();

            if (!isActive()) {
                return;
            }

            try {
                doBeginRead(); // 看这里
            } catch (final Exception e) {
                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        pipeline.fireExceptionCaught(e);
                    }
                });
                close(voidPromise());
            }
        }
```

`AbstractNioChannel`
```java
    @Override
    protected void doBeginRead() throws Exception {
        // Channel.read() or ChannelHandlerContext.read() was called
        final SelectionKey selectionKey = this.selectionKey;
        if (!selectionKey.isValid()) {
            return;
        }

        readPending = true;

        final int interestOps = selectionKey.interestOps();
        if ((interestOps & readInterestOp) == 0) {
            selectionKey.interestOps(interestOps | readInterestOp); // 找到你了！
        }
    }
```