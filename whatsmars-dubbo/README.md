### 高性能分布式RPC框架
- https://github.com/alibaba/dubbo
- dubbo请求调用过程分析 http://wely.iteye.com/blog/2378164

### User Guide
- 配置覆盖策略：java -D > xml > properties，properties适合全局配置
- 配置覆盖策略：reference method > service method > reference > service > consumer > provider
- 启动时检查：dubbo:reference check="true" Dubbo 缺省会在启动时检查依赖的服务是否可用，不可用时会抛出异常，阻止 Spring 初始化完成
- 集群容错模式：默认 cluster="failover"，其他 failfast,failsafe,failback,forking,broadcast
- 负载均衡：默认 loadbalance="random"，其他 roundrobin,leastactive,consistenthash
- 线程模型：<dubbo:protocol name="dubbo" dispatcher="all" threadpool="fixed" threads="100" />
- 直连提供者：<dubbo:reference id="xxxService" interface="com.alibaba.xxx.XxxService" url="dubbo://localhost:20890" />
- 只订阅(禁用注册)：<dubbo:registry address="10.20.153.10:9090" register="false" />
- 人工管理服务上下线：<dubbo:registry address="10.20.141.150:9090" dynamic="false" />
- 多协议：
```xml
<!-- 多协议配置 -->
<dubbo:protocol name="dubbo" port="20880" />
<dubbo:protocol name="hessian" port="8080" />
<!-- 使用多个协议暴露服务 -->
<dubbo:service id="helloService" interface="com.alibaba.hello.api.HelloService" version="1.0.0" protocol="dubbo,hessian" />
```
- 多注册中心：略
- 服务分组，同一接口不通实现
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