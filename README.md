# 🚀whatsmars

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://adoptium.net/zh-CN/temurin/releases)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

Java生态研究(**Spring Boot**🔥 + **Redis**🔥 + **Dubbo**🔥 + **RocketMQ**🔥 + **Elasticsearch**🔥)
- [Spring Boot](https://spring.io/projects/spring-boot) 提高生产力的利器
- [Redis](https://redis.io/) 缓存，NoSQL 数据库，分布式锁，位图
- [Apache Dubbo](http://dubbo.apache.org) / [Spring Cloud](https://spring.io/projects/spring-cloud) 分布式 RPC，微服务架构
- [Apache RocketMQ](https://rocketmq.apache.org/) / [Apache Kafka](http://kafka.apache.org/) 削峰填谷，异步解耦，顺序消息，延迟消息，事务消息
- [Elasticsearch](https://www.elastic.co) *搜索、分析和存储您的数据*

### 模块概览
| 模块                          | 描述                                     |
|-------------------------------|------------------------------------------|
| whatsmars-ai                  | LangChain4j 深度探索                     |
| whatsmars-common              | 通用公共模块，含 Java 并发包基础示例     |
| whatsmars-curator             | ZooKeeper 最佳客户端，含高级功能         |
| whatsmars-dubbo               | 高性能分布式RPC框架，Dubbo 3.3           |
| whatsmars-elasticsearch       | ElasticsearchClient 全方位示例           |
| whatsmars-grpc                | 跨语言的RPC框架，含与 Dubbo Triple 互通  |
| whatsmars-mcp                 | MCP Server，Streamable HTTP 协议暴露工具 |
| whatsmars-mq                  | 消息中间件RocketMQ/Kafka/RabbitMQ/Pulsar |
| whatsmars-nacos               | 注册与配置中心完整示例                   |
| whatsmars-netty               | NIO框架首选，基础到高级功能              |
| whatsmars-redis               | Redis 三种客户端，含多集群示例           |
| whatsmars-scheduling          | 分布式调度双方案                         |
| whatsmars-sentinel            | 流量控制与熔断降级组件                   |
| whatsmars-shardingsphere      | 分布式数据库中间件                       |
| whatsmars-spring              | Spring 核心功能完整示例                  |
| whatsmars-spring-boot-samples | Spring Boot Samples，20+个示例           |

### 系列项目

本仓库之外，还有几个围绕 **Java × AI** 的姊妹项目，构成从传统微服务到 AI 工程化的完整实践主线：

- **[jaws](https://github.com/javahongxi/jaws)** — 核心不到 2 万行的轻量级 RPC 框架，对标 Dubbo 核心机制：自研 HTTP/2 传输消除队头阻塞、Server Streaming、实测 10 万 QPS，一次调用链可以完整读到尾；亦支持将 RPC 服务自动暴露为 MCP Tools
- **[spacecloud](https://github.com/javahongxi/spacecloud)** — Spring AI 2.0 + Spring Boot 4.1 + Spring Cloud Alibaba 全栈微服务实践，HTTP / Dubbo / gRPC 多协议，AI 能力落地微服务的完整参考
- **[babi](https://github.com/javahongxi/babi)** — 深入实践 AI Coding Agent，基于 AgentScope / LangGraph / Spring AI 三大框架探索 Agent 工程化落地

&copy; [hongxi.org](http://hongxi.org)
