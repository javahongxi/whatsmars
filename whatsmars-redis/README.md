## Redis

### Redis 集群方案

目前国内 Redis 集群的主流方案主要分为三大类，其中 **Redis Cluster（官方原生集群）** 是目前生产环境中最主流的首选方案。

#### 1. 官方原生 Redis Cluster（生产主流首选）

Redis 3.0 之后官方推出的原生分布式集群方案，互联网和大型企业项目中最广泛采用的标准方案。

- **核心特点**：无中心化 P2P 架构，16384 个哈希槽（SLOT）动态分片，节点间 Gossip 协议通信
- **优势**：无代理层，性能损耗极低；原生支持自动故障转移和在线扩缩容（Resharding）
- **局限**：需要客户端支持集群协议（Smart Client）；不支持跨槽多 Key 操作
- **适用场景**：高并发、大数据量、需要水平扩展的生产环境

##### Redis Cluster 的主从复制与自动故障转移

Redis Cluster 中的每个主节点都可以挂载 0 个、1 个或多个从节点，生产环境通常建议每个主节点至少配置 1 个从节点。集群同时提供数据分片、主从复制和自动故障转移能力，无须再部署 Sentinel。

以常见的 **3 主 3 从** 架构为例：

```text
M1（哈希槽 0～5460）      → R1
M2（哈希槽 5461～10922）  → R2
M3（哈希槽 10923～16383） → R3
```

- **数据分片**：哈希槽只分配给主节点，从节点通过异步复制保存对应主节点的数据，用于故障接管
- **在线重新分片（Resharding）**：扩容或缩容主节点时，通过迁移哈希槽及其数据重新分配负载；从节点不固定拥有哈希槽，而是持续复制所属主节点当前负责的数据，无须单独为从节点分片。迁移期间集群仍可提供服务，支持集群协议的客户端通过 `MOVED`、`ASK` 重定向访问目标节点
- **自动故障转移**：主节点故障后，满足集群故障判定并获得多数主节点授权时，其从节点会被提升为新主节点，接管原主节点的哈希槽
- **读写方式**：客户端默认读写主节点；可以使用 `READONLY` 允许从从节点读取，但由于复制是异步的，可能读到旧数据
- **部署建议**：支持一主多从，例如 3 主 6 从；主从节点应尽量部署在不同机器、机架或可用区，避免同时故障
- **高可用限制**：也可以只部署主节点，但主节点宕机且没有可接管的从节点时，其负责的哈希槽将无法正常服务

#### 2. 云托管集群方案（企业级推荐）

阿里云、腾讯云等提供的托管版 Redis Cluster，底层基于原生 Cluster 或深度定制架构（如阿里云 Tair、腾讯 Tendis）。

- **优势**：自动故障转移、自动备份、一键扩容，具备 SLA 保证，大幅降低运维成本
- **局限**：成本相对较高，底层架构不可控
- **适用场景**：希望免去运维烦恼、追求高可用与稳定性的中大型企业

#### 3. 主从复制 + Sentinel 哨兵（中小型系统标配）

1 个主节点 + N 个从节点，配合奇数个 Sentinel 节点进行监控与自动故障转移。

- **优势**：部署简单，成熟稳定，对客户端几乎零侵入
- **局限**：写操作仍是单点，无法突破单机内存上限
- **适用场景**：QPS 2~3 万以内、数据量几十 GB 以内、读多写少且不需横向扩展的业务

#### 4. 代理型集群（Codis / Twemproxy）（历史方案）

- **Twemproxy**：轻量级代理，静态分片，不支持动态扩缩容
- **Codis**：企业级动态分片代理，支持可视化 Dashboard，但组件复杂，新项目已较少采用

#### Redis Cluster 与 Sentinel 哨兵模式的核心区别

| 维度           | Redis Cluster                                               | Sentinel（哨兵模式）                                |
|----------------|-------------------------------------------------------------|-----------------------------------------------------|
| **定位**       | 官方原生分布式集群方案                                      | 高可用（HA）方案，不解决分布式问题                  |
| **架构**       | 无中心化 P2P，多主多从                                      | 单主多从 + 独立 Sentinel 进程监控                   |
| **数据分布**   | 16384 个哈希槽分片到多个主节点                              | 所有数据在同一个主节点，从节点仅做冗余              |
| **水平扩展**   | 支持在线 Resharding，动态增减主节点                         | 写操作始终单主，无法突破单机内存上限                |
| **故障转移**   | 节点间 Gossip 协议投票自动完成，无需外部组件                | 依赖独立 Sentinel 进程（奇数个）间共识完成          |
| **客户端要求** | 需支持集群协议（Smart Client），处理 `MOVED` / `ASK` 重定向 | 几乎零侵入，客户端连接 Sentinel 获取主节点地址即可  |
| **部署门槛**   | 至少 3 主节点（推荐 3 主 3 从），运维相对复杂               | 1 主 1 从 + 3 个 Sentinel 即可，部署简单            |
| **适用场景**   | 高并发、大数据量、需要水平扩展的生产环境                    | QPS 2~3 万以内、数据量几十 GB、读多写少的中小型系统 |

**一句话总结**：Sentinel 解决的是"主节点挂了能自动切换"的**高可用**问题，Cluster 在此基础上额外解决了"单机装不下"的**分布式分片**问题。如果数据量和并发没有突破单机瓶颈，Sentinel 是更简单经济的选择。

**总结建议**：新建互联网项目或中大型系统，首选 **Redis Cluster** 或 **云托管 Redis 集群**；中小型业务或读多写少场景，**主从 + 哨兵** 依然是性价比最高的选择。

### 缓存三大经典问题

| 问题     | 现象                              | 解决方案                                   |
|----------|-----------------------------------|--------------------------------------------|
| 缓存穿透 | 查询不存在的数据，绕过缓存直击 DB | 布隆过滤器拦截 + 缓存空值                  |
| 缓存击穿 | 热点 Key 过期瞬间大量请求涌入     | 互锁（Redisson 分布式锁）+ 逻辑过期        |
| 缓存雪崩 | 大量 Key 同时过期                 | 过期时间加随机值 + 多级缓存架构 + 缓存预热 |

#### 缓存穿透：布隆过滤器开源实现

布隆过滤器（Bloom Filter）是一种空间效率极高的概率数据结构，用于快速判断元素**是否可能存在**。在缓存穿透场景中，查询缓存前先经过布隆过滤器判断，若不存在则直接拦截，避免不存在的 Key 请求直达 DB。

**方案一：Guava BloomFilter（单机场景首选）**

Google Guava 库内置的布隆过滤器实现，API 简洁，适合单 JVM 内的防穿透场景。

```java
// 创建：预期插入 100 万个元素，误判率 1%
BloomFilter<String> bloomFilter = BloomFilter.create(
    Funnels.stringFunnel(Charset.defaultCharset()),
    1_000_000,
    0.01
);

// 数据缓存时写入布隆过滤器
bloomFilter.put("user:1001");

// 查询前先判断
if (bloomFilter.mightContain("user:1001")) {
    // 可能存在，查缓存 / DB
} else {
    // 一定不存在，直接拦截
}
```

- **优势**：使用简单、零额外依赖
- **局限**：不支持删除（标准布隆过滤器特性）；多实例间数据不共享，需各节点独立维护或同步副本
- **适用场景**：单机部署、可接受短暂数据不一致的业务

**方案二：RedisBloom（分布式场景首选）**

Redis 官方模块，原生提供布隆过滤器能力，支持多个应用实例共享同一个过滤器，保证数据一致。

```bash
# 安装 RedisBloom 模块（Docker 方式）
docker run -p 6379:6379 redis/redis-stack:latest

# Redis CLI 操作
BF.ADD mybloom "user:1001"        # 添加元素
BF.EXISTS mybloom "user:1001"     # 判断是否存在，返回 1 = 可能存在，0 = 一定不存在
BF.RESERVE mybloom 0.01 1000000   # 预先创建：错误率 0.01，预期容量 100 万
```

- **优势**：分布式共享、支持动态扩容、支持持久化
- **局限**：需额外部署 RedisBloom 模块，增加运维成本
- **适用场景**：多实例部署、对数据一致性要求高的生产环境穿透防护

#### 缓存击穿：互斥重建与逻辑过期

缓存击穿针对**单个热点 Key**：其过期瞬间，海量并发请求同时穿透缓存落到 DB，造成瞬时压力尖峰。核心思路是「只让一个请求去重建缓存，其余请求等待或返回旧值」。

**方案一：互斥锁（强一致性首选）**

借助 Redisson 分布式锁，抢到锁的线程查 DB 并回写缓存，其余线程阻塞等待后重试命中缓存。以热 Key 重建为例：

```java
public User getUserById(String id) {
    User user = cache.get("user:" + id);
    if (user != null) {
        return user;
    }
    // 缓存未命中，尝试获取分布式锁（仅锁住重建过程，读缓存不加锁）
    RLock lock = redissonClient.getLock("lock:user:" + id);
    lock.lock();
    try {
        // 双重检查：可能已被其他线程回写
        user = cache.get("user:" + id);
        if (user == null) {
            user = db.query(id);            // 查 DB
            if (user != null) {
                cache.put("user:" + id, user, 30, TimeUnit.MINUTES);
            }
        }
        return user;
    } finally {
        lock.unlock();
    }
}
```

- **优势**：实现简单、数据强一致，缓存中始终是最新值
- **局限**：其余线程需阻塞等待，存在一定耗时与潜在死锁风险（需设置锁超时）
- **适用场景**：对一致性要求高、可接受短暂等待的热点 Key 重建

**方案二：逻辑过期（高并发可用性首选）**

不给 Key 设置真实的 Redis TTL，而是在 Value 中内嵌一个「逻辑过期时间」。发现逻辑过期后，由抢到锁的线程**异步**重建缓存，当前请求直接返回旧值，不阻塞。

```java
// Value 包装：数据 + 逻辑过期时间戳
@Data
@AllArgsConstructor
public class CacheData<T> {
    private T data;
    private long expireAt;   // 逻辑过期时间（非 Redis TTL）
}

public User getUserById(String id) {
    CacheData<User> wrapper = cache.get("user:" + id);
    if (wrapper == null) {
        return loadAndCache(id);            // 首次无缓存，同步加载
    }
    if (System.currentTimeMillis() < wrapper.getExpireAt()) {
        return wrapper.getData();           // 未过期，直接返回
    }
    // 已逻辑过期：尝试抢锁，抢到则异步重建，本次仍返回旧值
    RLock lock = redissonClient.getLock("rebuild:user:" + id);
    if (lock.tryLock()) {
        try {
            asyncExecutor.submit(() -> {
                try { loadAndCache(id); }    // 后台重建
                finally { lock.unlock(); }
            });
        } catch (RejectedExecutionException e) {
            lock.unlock();                   // 线程池满则释放锁，交下次请求重试
        }
    }
    return wrapper.getData();               // 不阻塞，返回旧值
}
```

- **优势**：请求永不阻塞、吞吐量高，彻底消除瞬时 DB 尖峰
- **局限**：重建期间会短暂返回旧数据（最终一致）；需额外维护逻辑过期字段与线程池
- **适用场景**：读多写少、可接受短暂不一致的超高并发热点（如秒杀、热门商品）

#### 缓存雪崩：错峰过期与多级缓存

缓存雪崩针对**大量 Key 在同一时刻集中失效**（或缓存服务整体宕机），导致请求洪峰瞬间全部涌向 DB，容易压垮数据库并引发连锁故障。核心思路是「削峰 + 兜底」。

**方案一：过期时间加随机值（错峰失效）**

批量写入或更新缓存时，在基础过期时间上叠加一个随机偏移量，避免 Key 集中过期。这是成本最低、见效最直接的手段：

```java
// 基础 TTL 30 分钟，叠加 0~5 分钟随机抖动，将失效时间打散
int baseTtl = 30 * 60;
int randomTtl = baseTtl + ThreadLocalRandom.current().nextInt(0, 5 * 60);
cache.put(key, value, randomTtl, TimeUnit.SECONDS);
```

- **优势**：实现极简、无额外依赖，能有效打散失效峰值
- **局限**：仅缓解「集中过期」，无法应对缓存服务整体宕机
- **适用场景**：因批量 Key 同时过期引发的常规雪崩

**方案二：多级缓存架构（请求分流）**

构建「本地缓存（Caffeine）→ 分布式缓存（Redis）→ DB」多级体系。即便 Redis 失效，本地缓存仍可拦截大部分请求，避免直接击穿到 DB：

```java
// L1 本地缓存：进程内，容量小、速度快，兜底 Redis 不可用
Cache<String, User> localCache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .expireAfterWrite(Duration.ofMinutes(5))
    .build();

public User getUser(String id) {
    // L1：本地缓存
    User user = localCache.getIfPresent(id);
    if (user != null) {
        return user;
    }
    // L2：Redis（异常时降级，继续走 DB 而非直接失败）
    try {
        user = redisCache.get("user:" + id);
    } catch (Exception e) {
        log.warn("Redis unavailable, degrade to DB", e);
    }
    if (user != null) {
        localCache.put(id, user);
        return user;
    }
    // L3：DB 兜底，并回填各级缓存
    user = db.query(id);
    if (user != null) {
        localCache.put(id, user);
        redisCache.put("user:" + id, user, ttlWithJitter());
    }
    return user;
}
```

- **优势**：层层拦截、可用性极强，Redis 整体故障时仍能降级服务
- **局限**：本地缓存存在多实例一致性延迟，需设置较短 TTL 或引入失效通知机制
- **适用场景**：高并发核心链路、对可用性要求极高的系统

**方案三：缓存预热与熔断限流（兜底防护）**

- **缓存预热**：系统启动或低峰期，主动将热点数据加载到缓存，避免上线瞬间大量冷 Key 直接穿透到 DB。
- **熔断限流**：DB 侧设置连接池上限与 Sentinel/Hystrix 熔断降级，当缓存大面积失效、DB 压力超阈值时及时熔断，返回兜底数据而非拖垮整个服务。

- **优势**：从源头减少冷 Key 冲击，熔断为极端场景提供最后防线
- **局限**：预热需识别真实热点、熔断降级需设计合理的兜底返回策略
- **适用场景**：大促、秒杀等瞬时高流量，以及对整体链路稳定性要求高的生产环境
