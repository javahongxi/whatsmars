# [Apache Dubbo](http://dubbo.apache.org)
高性能分布式RPC框架 👻 [start.dubbo.io](http://start.dubbo.io)

> Dubbo框架的实现充分践行了可扩展性，即**类→抽象类→接口**+SPI。我们平时在spring-boot环境下，也可利用**类→抽象类→接口**+AutoConfiguration实现可扩展性，一些场景下要学会用**面向接口编程**代替if-else。

### 为什么zookeeper不适合做服务注册中心
有个思考，从CAP角度考虑，服务注册中心是CP系统还是AP系统呢？
首先，服务注册中心是为了服务间调用服务的，那么绝对不允许因为服务注册中心出现了问题而导致服务间的调用出问题。
再者，假如有node1,node2,node3集群节点，保存着可用服务列表ip1,ip2,ip3，试想如果此时不一致，比如node1只保存了ip1,ip2，
此时调用方读取node1节点，那么会造成什么影响？调用node1上注册的服务，顶多就是负载均衡时不会有流量打到ip3，然后等node1同步回ip3后，
又一致了，这对服务其实没什么太大影响。所以，服务注册中心应该是个AP系统。而zookeeper是个CP系统，强一致性。
- 场景1，当master挂了，此时zookeeper集群需要重新选举，而此时调用方需要来读取服务列表，是不可用的，影响到了服务的可用性。
当然你可以说本地有缓存可用列表，然而下面这种方式就更无法处理了。
- 场景2，分区可用。试想，有3个机房，如果其中机房3和机房1,2之间的网络断了，那么机房3的注册中心就不能注册新的机器了么，这显然也不合理。

*当然，大部分场景下，zookeeper做服务注册中心仍然是一个不错的选择*

### Test
- startup zookeeper
- mvn clean package
- cd whatsmars-dubbo-provider/target
- java -jar -Ddubbo.registry.address=zookeeper://127.0.0.1:2181 whatsmars-dubbo-provider.jar
- cd whatsmars-dubbo-consumer/target
- java -jar -Ddubbo.registry.address=zookeeper://127.0.0.1:2181 whatsmars-dubbo-consumer.jar

### User Guide
- [Dubbo框架设计](https://github.com/javahongxi/whatsmars/wiki/Dubbo%E6%A1%86%E6%9E%B6%E8%AE%BE%E8%AE%A1)
- 配置覆盖策略：java -D > xml > properties，properties适合全局配置。本地调试时，可利用此特性在
IDEA VM options 设置 -Ddubbo.registry.register=false (有id时为-Ddubbo.registry.xx.register=false)
- 配置覆盖策略：reference method > service method > reference > service > consumer > provider
- 启动时检查：dubbo:reference check="true" Dubbo 缺省会在启动时检查依赖的服务是否可用，不可用时会抛出异常，阻止 Spring 初始化完成
- 集群容错模式：默认 cluster="failover"，其他 failfast,failsafe,failback,forking,broadcast
- 负载均衡：默认 loadbalance="random"，其他 roundrobin,leastactive,consistenthash
- 线程模型：默认 <dubbo:protocol name="dubbo" dispatcher="all" threadpool="fixed" threads="200" />
  + 如果事件处理的逻辑能迅速完成，并且不会发起新的 IO 请求，比如只是在内存中记个标识，则直接在 IO 线程上处理更快，因为减少了线程池调度。
  + 但如果事件处理逻辑较慢，或者需要发起新的 IO 请求，比如需要查询数据库，则必须派发到线程池，否则 IO 线程阻塞，将导致不能接收其它请求。
  + 如果用 IO 线程处理事件，又在事件处理过程中发起新的 IO 请求，比如在连接事件中发起登录请求，会报“可能引发死锁”异常，但不会真死锁。
  + all 所有消息都派发到线程池，包括请求，响应，连接事件，断开事件，心跳等。
  + message 只有请求响应消息派发到线程池，其它连接断开事件，心跳等消息，直接在 IO 线程上执行。
- 直连提供者：<dubbo:reference id="xxxService" interface="com.alibaba.xxx.XxxService" url="dubbo://localhost:20890" />
- 只订阅(禁用注册)：<dubbo:registry address="10.20.153.10:9090" register="false" />
- 人工管理服务上下线：<dubbo:registry address="10.20.141.150:9090" dynamic="false" />
- 多协议：
```xml
<!-- 多协议配置，一种协议只能对应一种序列化方式，建议用hessian2序列化
    （也是官方推荐，出于稳定性和性能的折中考虑）
     序列化方式由provider决定，所以团队要决定改序列化方式时，只需修改provider配置 -->
<dubbo:protocol name="dubbo" port="20880" />
<dubbo:protocol name="hessian" port="8080" />
<!-- 使用多个协议暴露服务 -->
<dubbo:service id="helloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0"
 protocol="dubbo,hessian" />
```
- 多注册中心：略
- 服务分组，同一接口不同实现
```xml
<dubbo:service group="index1" interface="com.xxx.IndexService" ref="indexService1" />
<dubbo:service group="index2" interface="com.xxx.IndexService" ref="indexService2" />
```
- 多版本，类似于服务分组
```xml
<dubbo:service interface="com.foo.BarService" version="1.0.0" ref="barService1" />
<dubbo:service interface="com.foo.BarService" version="2.0.0" ref="barService2" />
```
- 分组聚合，按组合并返回结果
```xml
<dubbo:reference interface="com.xxx.MenuService" group="*" merger="true" />
<dubbo:reference interface="com.xxx.MenuService" group="aaa,bbb" merger="true" />
```
- 结果缓存
```xml
<dubbo:reference interface="com.foo.BarService">
    <dubbo:method name="findBar" cache="lru" />
</dubbo:reference>
```
- 泛化引用
```xml
<dubbo:reference id="barService" interface="com.foo.BarService" generic="true" />
```
```java
GenericService barService = (GenericService) applicationContext.getBean("barService");
Object result = barService.$invoke("sayHello", new String[] { "java.lang.String" }, new Object[] { "World" });
```
- 回声测试，所有服务自动实现 EchoService 接口，只需将任意服务引用强制转型为 EchoService，即可使用
```java
// 远程服务引用
MemberService memberService = ctx.getBean("memberService");
EchoService echoService = (EchoService) memberService; // 强制转型为EchoService
// 回声测试可用性
String status = echoService.$echo("OK");
assert(status.equals("OK"));
```
- 上下文信息
<br>RpcContext 是一个 ThreadLocal 的临时状态记录器，当接收到 RPC 请求，或发起 RPC 请求时，
RpcContext 的状态都会变化。比如：A 调 B，B 再调 C，则 B 机器上，在 B 调 C 之前，RpcContext
记录的是 A 调 B 的信息，在 B 调 C 之后，RpcContext 记录的是 B 调 C 的信息。
- 隐式参数
```java
// 隐式传参，后面的远程调用都会隐式将这些参数发送到服务器端，类似cookie，用于框架集成，不建议常规业务使用
RpcContext.getContext().setAttachment("index", "1");
xxxService.xxx(); // 远程调用
```
- 本地伪装，通常用于降级：<dubbo:service interface="com.foo.BarService" mock="com.foo.BarServiceMock" />
<br>如果服务的消费方经常需要 try-catch 捕获异常，请考虑改为 Mock 实现，并在 Mock 实现中 return null。
如果只是想简单的忽略异常，用mock="return null"即可
- 服务降级，向注册中心写入动态配置覆盖规则：
```java
RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://10.20.153.10:2181"));
// mock=force:return+null mock=fail:return+null
registry.register(URL.valueOf("override://0.0.0.0/com.foo.BarService?category=configurators&dynamic=false&application=foo&mock=fail:return+null"));
```
- 延迟暴露
```xml
<!-- 延迟 5 秒暴露服务 -->
<dubbo:service delay="5000" />
<!-- 延迟到 Spring 初始化完成后，再暴露服务 -->
<dubbo:service delay="-1" />
```
- 并发控制，异步调用，本地调用，参数回调，事件通知 ...

### More
- [《企业IT架构转型之道：阿里巴巴中台战略思想与架构实战》](https://book.douban.com/subject/27039508/)
- [《亿级流量网站架构核心技术》](https://book.douban.com/subject/26999243/) `douban.com`
- [《从Paxos到Zookeeper》](https://book.douban.com/subject/26292004/) `douban.com`
- [《Netty权威指南》](http://e.jd.com/30186249.html) `e.jd.com`
- [关于分布式事务](https://blog.csdn.net/javahongxi/article/details/54177741)