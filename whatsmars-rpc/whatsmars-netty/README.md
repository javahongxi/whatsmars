# Netty Examples

### EventLoop & EventExecutor
Netty的线程模型中有4个基础接口，它们分别是EventLoopGroup、EventLoop、EventExecutorGroup、EventExecutor。
其中EventExecutor扩展自java.util.concurrent.ScheduledExecutorService接口，类似于线程池（执行）的职责，
而EventLoop首先继承自EventExecutor，并主要扩展了register方法,就是将通道Channel注册到Selector的方法。
NioEventLoop,就是基于Nio的实现,不同OS实现不同，Linux是EpollEventLoop, Mac OS 是KQueueEventLoop。
在这个类中有一个亮点，就是规避了JDK nio的一个bug,Selector select方法的空轮询,核心思想是，如果连续多少
次（默认为512）在没有超时的情况就返回，并且已经准备就绪的键的数量为0，则认为发生了空轮询，如果发生了空轮询，
就新建一个新的Selector,并重新将通道，关心的事件注册到新的Selector, 并关闭旧的Selector。

```java
public interface EventExecutorGroup extends ScheduledExecutorService, Iterable<EventExecutor>

public interface EventExecutor extends EventExecutorGroup

public interface OrderedEventExecutor extends EventExecutor

public interface EventLoopGroup extends EventExecutorGroup

public interface EventLoop extends OrderedEventExecutor, EventLoopGroup
```

### 4.x和3.x的线程模型最大区别
4.x中ChannelPipeline中的Handler链统一由I/O线程串行调度，无论是读还是写操作，3.x中的write操作时
由业务线程处理Handler链。4.x中可以降低线程之间的上下文切换带来的时间消耗，但是3.x中业务线程可以并
发执行Handler链。如果有一些耗时的Handler操作会导致4.x的效率低下，但是可以考虑将这些耗时操作放在业
务线程最先执行，不放在Handler里处理。由于业务线程可以并发执行，同样也可以提高效率。

4.x的串行化线程模型设计

![img](https://github.com/javahongxi/static/blob/master/netty_01.png)

一个NioEventLoop聚合了一个多路复用器Selector，因此可以处理成百上千的客户端连接，Netty的处理策略
是每当有一个新的客户端接入，则从NioEventLoop线程组中顺序获取一个可用的NioEventLoop，当到达数组上
限之后，重新返回到0，通过这种方式，可以基本保证各个NioEventLoop的负载均衡。一个客户端连接只注册到
一个NioEventLoop上，这样就避免了多个I/O线程去并发操作它。

Netty通过串行化设计理念降低了用户的开发难度，提升了处理性能。利用线程组实现了多个串行化线程水平并
行执行，线程之间并没有交集，这样既可以充分利用多核提升并行处理能力，同时避免了线程上下文的切换和并
发保护带来的额外性能损耗。

了解完了Netty 4的串行化设计理念之后，我们继续看Netty 3线程模型存在的问题，总结起来，它的主要问题如下：

- Inbound和Outbound实质都是I/O相关的操作，它们的线程模型竟然不统一，这给用户带来了更多的学习和使用成本；
- Outbound操作由业务线程执行，通常业务会使用线程池并行处理业务消息，这就意味着在某一个时刻会有多个业务线程同时操作ChannelHandler，我们需要对ChannelHandler进行并发保护，通常需要加锁。如果同步块的范围不当，可能会导致严重的性能瓶颈，这对开发者的技能要求非常高，降低了开发效率；
- Outbound操作过程中，例如消息编码异常，会产生Exception，它会被转换成Inbound的Exception并通知到ChannelPipeline，这就意味着业务线程发起了Inbound操作，即需要将该异常交由I/O线程去处理，从而产生了额外的线程的上下文切换！它打破了Inbound操作由I/O线程操作的模型，如果开发者按照Inbound操作只会由一个I/O线程执行的约束进行设计，则会发生线程并发访问安全问题。由于该场景只在特定异常时发生，因此错误非常隐蔽！一旦在生产环境中发生此类线程并发问题，定位难度和成本都非常大。

### ServerBootstrap bind
```java
    private ChannelFuture doBind(final SocketAddress localAddress) {
        final ChannelFuture regFuture = initAndRegister();
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
            // Registration future is almost always fulfilled already, but just in case it's not.
            final PendingRegistrationPromise promise = new PendingRegistrationPromise(channel);
            regFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    Throwable cause = future.cause();
                    if (cause != null) {
                        // Registration on the EventLoop failed so fail the ChannelPromise directly to not cause an
                        // IllegalStateException once we try to access the EventLoop of the Channel.
                        promise.setFailure(cause);
                    } else {
                        // Registration was successful, so set the correct executor to use.
                        // See https://github.com/netty/netty/issues/2586
                        promise.registered();

                        doBind0(regFuture, channel, localAddress, promise);
                    }
                }
            });
            return promise;
        }
    }
```

- 初始化一个通道，并注册，如果注册失败，直接返回。
- 如果初始化并立即注册成功，执行doBind0方法，进行绑定
- 如果未立即注册成功，则添加监听事件，等初始化并注册成功后，执行doBind0方法，这里是Netty对jdk
Future模式进行的扩展，引入事件机制

### group().register(channel)
```java
    final ChannelFuture initAndRegister() {
        Channel channel = null;
        try {
            channel = channelFactory.newChannel();
            init(channel); // NioServerSocketChannel
        } catch (Throwable t) {
            if (channel != null) {
                channel.unsafe().closeForcibly();
                return new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE).setFailure(t);
            }
            return new DefaultChannelPromise(new FailedChannel(), GlobalEventExecutor.INSTANCE).setFailure(t);
        }

        ChannelFuture regFuture = config().group().register(channel);
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

根据Netty线程模型，group()返回的是EventLoopGroup，然后Netty会从该EventLoopGroup中获取下一个
EventLoop来执行。由于程序入口使用的是NioServerSocketChannel,故本例最终会使用NioEventLoop
来作为事件处理器来服务，这里的register方法，其实现在NioEventLoop的父类SingleThreadEventLoop中。

```java
    @Override
    public ChannelFuture register(Channel channel) {
        return register(new DefaultChannelPromise(channel, this));
    }

    @Override
    public ChannelFuture register(final ChannelPromise promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        promise.channel().unsafe().register(this, promise);
        return promise;
    }
```

有关Channel的IO类操作，最终都会转发给Unsafe类去执行，直接进入到AbstractUnsafe中

```java
        @Override
        public final void register(EventLoop eventLoop, final ChannelPromise promise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            }
            if (isRegistered()) {
                promise.setFailure(new IllegalStateException("registered to an event loop already"));
                return;
            }
            if (!isCompatible(eventLoop)) {
                promise.setFailure(
                        new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
                return;
            }

            AbstractChannel.this.eventLoop = eventLoop;

            if (eventLoop.inEventLoop()) {
                register0(promise);
            } else {
                try {
                    eventLoop.execute(new Runnable() {
                        @Override
                        public void run() {
                            register0(promise);
                        }
                    });
                } catch (Throwable t) {
                    logger.warn(
                            "Force-closing a channel whose registration task was not accepted by an event loop: {}",
                            AbstractChannel.this, t);
                    closeForcibly();
                    closeFuture.setClosed();
                    safeSetFailure(promise, t);
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
                doRegister(); // @@@@@ 1
                neverRegistered = false;
                registered = true;

                // Ensure we call handlerAdded(...) before we actually notify the promise. This is needed as the
                // user may already fire events through the pipeline in the ChannelFutureListener.
                pipeline.invokeHandlerAddedIfNeeded();

                safeSetSuccess(promise);
                pipeline.fireChannelRegistered(); // @@@@@ 2
                // Only fire a channelActive if the channel has never been registered. This prevents firing
                // multiple channel actives if the channel is deregistered and re-registered.
                if (isActive()) {
                    if (firstRegistration) {
                        pipeline.fireChannelActive();
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

完成Channel的注册外，需要调用管道的pipline.fireChannelRegistered,跟踪进去，最终将执行
DefaultChannelHandlerInvoker的invokeChannelRegistered方法，该方法会执行ChannelInitializer的init方法。

### ChannelPipeline
设计理念：ChannelPipeline管道，提供相应的API,增加ChannelHander形成处理链条，在DefaultChannelPipeline
中并不是用一个LikedList<ChannelHander> 来实现链表，而是在其自身就是一个链表结构，链表的节点是
AbstractChannelHandlerContext，里面有next,与perv分别指向下一个或上一个节点。
在DefaultChannelHanderPipeline中持有tail与head引用。

```java
    public ChannelPipeline fireChannelRegistered() {
        head.fireChannelRegistered();
        return this;
    }
    @Override
    public ChannelPipeline read() {
        tail.read();
        return this;
    }
    @Override
    public ChannelFuture write(Object msg) {
        return tail.write(msg);
    }
```

从上述方法，不难看出,ChannelPipeline的实现源码，就是沿着调用链向上或向下传播事件并执行之。
其实ChannelPipeline并没有什么高深的地方，其设计哲学就是职责链模式。

### ChannelHandlers的执行顺序
pipeline里的handlers分为两类，分别实现了ChannelInboundHandler和ChannelOutboundHandler接口。
ChannelInboundHandler对从客户端发往服务器的报文进行处理，一般用来执行解码、读取客户端数据、进行业务处理等；
ChannelOutboundHandler对从服务器发往客户端的报文进行处理，一般用来进行编码、发送报文到客户端。
ChannelInboundHandler按照注册的先后顺序执行，ChannelOutboundHandler按照注册的先后顺序逆序执行。

### 线程安全
ChannelPipeline与SocketChannel绑定，是线程安全的。标注@Sharable的ChannelHandler必须是线程安全的，如ChannelInitializer。
