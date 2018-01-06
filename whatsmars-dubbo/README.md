### 高性能分布式RPC框架
- https://github.com/alibaba/dubbo
- dubbo请求调用过程分析 http://wely.iteye.com/blog/2378164

### Test
- startup zookeeper
- mvn clean package
- cd whatsmars-dubbo-provider/target
- java -jar -Ddubbo.registry.address=zookeeper://127.0.0.1:2181 whatsmars-dubbo-provider.jar
- cd whatsmars-dubbo-consumer/target
- java -jar -Ddubbo.registry.address=zookeeper://127.0.0.1:2181 whatsmars-dubbo-consumer.jar

### User Guide
- http://dubbo.io/books/dubbo-user-book
- 配置覆盖策略：java -D > xml > properties，properties适合全局配置
- 配置覆盖策略：reference method > service method > reference > service > consumer > provider
- 启动时检查：dubbo:reference check="true" Dubbo 缺省会在启动时检查依赖的服务是否可用，不可用时会抛出异常，阻止 Spring 初始化完成
- 集群容错模式：默认 cluster="failover"，其他 failfast,failsafe,failback,forking,broadcast
- 负载均衡：默认 loadbalance="random"，其他 roundrobin,leastactive,consistenthash
- 线程模型：默认 <dubbo:protocol name="dubbo" dispatcher="all" threadpool="fixed" threads="100" />
1. 如果事件处理的逻辑能迅速完成，并且不会发起新的 IO 请求，比如只是在内存中记个标识，则直接在 IO 线程上处理更快，因为减少了线程池调度。
2. 但如果事件处理逻辑较慢，或者需要发起新的 IO 请求，比如需要查询数据库，则必须派发到线程池，否则 IO 线程阻塞，将导致不能接收其它请求。
3. 如果用 IO 线程处理事件，又在事件处理过程中发起新的 IO 请求，比如在连接事件中发起登录请求，会报“可能引发死锁”异常，但不会真死锁。
4. all 所有消息都派发到线程池，包括请求，响应，连接事件，断开事件，心跳等。
5. message 只有请求响应消息派发到线程池，其它连接断开事件，心跳等消息，直接在 IO 线程上执行。
- 直连提供者：<dubbo:reference id="xxxService" interface="com.alibaba.xxx.XxxService" url="dubbo://localhost:20890" />
- 只订阅(禁用注册)：<dubbo:registry address="10.20.153.10:9090" register="false" />
- 人工管理服务上下线：<dubbo:registry address="10.20.141.150:9090" dynamic="false" />
- 多协议：
```xml
<!-- 多协议配置 -->
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
- 本地伪装：<dubbo:service interface="com.foo.BarService" mock="com.foo.BarServiceMock" />
- 延迟暴露
```xml
<!-- 延迟 5 秒暴露服务 -->
<dubbo:service delay="5000" />
<!-- 延迟到 Spring 初始化完成后，再暴露服务 -->
<dubbo:service delay="-1" />
```
- 并发控制，异步调用，本地调用，参数回调，事件通知 ...
- 最佳实践 https://github.com/javahongxi/whatsmars/blob/master/whatsmars-dubbo/best-practice.md
- 推荐用法 https://github.com/javahongxi/whatsmars/blob/master/whatsmars-dubbo/recommend.md