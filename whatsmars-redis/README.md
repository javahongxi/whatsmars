# [Redis](https://github.com/antirez/redis)
Redis is an in-memory database that persists on disk. The data model is key-value,
but many different kind of values are supported: Strings, Lists, Sets, Sorted Sets,
Hashes, HyperLogLogs, Bitmaps. https://redis.io http://redis.net.cn

### Spring Data Redis
- Spring Data’s mission is to provide a familiar and consistent, Spring-based programming model
for data access while still retaining the special traits of the underlying data store.
- SDR对Redis的标准模式和Cluster模式进行了充分封装，但并未对客户端sharding进行良好封装，需要开发者自己实现，
这也是SDR和[Jedis](https://github.com/xetorthio/jedis)相比，唯一缺少的特性。另外，Redis官网给出了一个
Redis的Java客户端列表，SDR支持Jedis, [Lettuce](https://github.com/lettuce-io/lettuce-core) ['lɛtɪs]，
Spring Boot 2.x默认使用Lettuce。
- Jedis相比Lettuce和[Redission](https://github.com/redisson/redisson)，最大的特点是简单易集成，这从源代码量就可感受到。

### Redis集群方案
- 客户端分片: 操作简单，无法动态扩缩容 `现实方案`
- [Twemproxy](https://github.com/twitter/twemproxy)/[Codis](https://github.com/CodisLabs/codis): `多余方案`
- Redis Cluster: 官方集群方案，无中心结构，动态扩缩容，最大支撑1000个节点，`理想方案`

### 主从读写分离
通常情况下，Slave只是作为数据备份，不提供read操作，这种考虑是为了避免slave提供stale数据而导致一些问题。
不过在很多场景下，即使slave数据有一定的延迟，我们仍然可以兼容或者正常处理，此时我们可以将slave提供read
服务，并在M-S集群中将read操作分流，此时我们的Redis集群将可以支撑更高的QPS。本实例中，仅仅提供了“读写分
离”的样板，尚未对所有的redis方法进行重写和封装，请开发者后续继续补充即可。此外，slave节点如果异常，我们
应该支持failover。

### Thread or epoll
MySQL为什么使用线程的模型而不是epoll之类的技术？
MySQL的主要任务是把数据组织成树表，在磁盘和内存之间进行转换，并且进行大量的查找和排序，所有这些操作花费的时间都很长。
假设每次操作平均占用30ms，而通过网络返回只需要1ms，则总花费31ms。时间主要花费在CPU和内存占用，而不是网络等待上。
所以，非阻塞io没有优势。而应用服务器，主要是接入客户端，对客户端认证，然后通过远程访问数据库，之后就是等待期，
应用服务器大部分时间花费在等待数据库结果以及各种远程服务比如某种密钥计算或者认证。那么Redis为什么用非阻塞？
主要是Redis的数据目标是存储到内存，而且没有大密度查找排序这种计算，所以Redis的计算很快，也就不存在io问题。

### MORE
- [知乎技术分享：从单机到2000万QPS并发的Redis高性能缓存实践之路](https://blog.csdn.net/javahongxi/article/details/82766742)
- [Connection and Thread Safety](https://blog.csdn.net/javahongxi/article/details/50559829)
- [如何根据key前缀统计内存占用](https://segmentfault.com/q/1010000010575235)
- [《Redis设计与实现》](https://e.jd.com/30189715.html) `e.jd.com`

[whatsmars-boot-sample-redis](https://github.com/javahongxi/whatsmars/tree/master/whatsmars-spring-boot-samples/whatsmars-boot-sample-redis)