# Netty Examples

### EventLoop & EventLoopGroup
Netty的线程模型，中有4个基础接口，它们分别是EventLoopGroup、EventLoop、EventExecuteGroup、EventExecute。
其中EventExecute扩展自java.util.concurrent.ScheduledExecutorService接口，类似与线程池（执行）的职责，
而EventLoop首先继承自EventExecute，并主要扩展了register方法,就是将通道Channel注册到Selector的方法。
NioEventLoop,就是基于Nio的实现。在这个类中有一个亮点，就是规避了JDK nio的一个bug,Selector select
方法的空轮询,核心思想是，如果连续多少次（默认为512）在没有超时的情况就返回，并且已经准备就绪的键的数量为0，
则认为发生了空轮询，如果发生了空轮询，就新建一个新的Selector,并重新将通道，关心的事件注册到新的Selector,
并关闭旧的Selector
