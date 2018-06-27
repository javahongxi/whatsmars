### 基于M-S模式下读写分离

通常情况下，Slave只是作为数据备份，不提供read操作，这种考虑是为了避免slave提供stale数据而导致一些问题。
不过在很多场景下，即使slave数据有一定的延迟，我们仍然可以兼容或者正常处理，此时我们可以将slave提供read
服务，并在M-S集群中将read操作分流，此时我们的Redis集群将可以支撑更高的QPS。本实例中，仅仅提供了“读写分
离”的样板，尚未对所有的redis方法进行重写和封装，请开发者后续继续补充即可。此外，slave节点如果异常，我们
应该支持failover，这一部分特性后续再扩展。

Jedis连接方式
[jedis](jedis.png)

https://github.com/antirez/redis