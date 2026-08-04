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

### AI 能力版图
| 项目           | 聚焦方向                        | 核心框架               | 语言          |
|----------------|---------------------------------|------------------------|---------------|
| **whatsmars**  | ai模块聚焦 langchain4j 深度探索 | **LangChain4j**        | Java          |
| **spacecloud** | ai模块聚焦 Spring AI 2.0 应用   | **Spring AI 2.0**      | Java          |
| **babi**       | AI Coding Agent                 | AgentScope / LangGraph | Java / Python |
| **jaws**       | mcp模块支持 RPC 转 MCP 服务     | MCP Java SDK           | Java          |

- **[whatsmars](https://github.com/javahongxi/whatsmars)** — ai模块聚焦 LangChain4j 生态，深度探索其原理和应用
- **[spacecloud](https://github.com/javahongxi/spacecloud)** — ai模块聚焦 Spring AI 2.0 在微服务场景的应用
- **[babi](https://github.com/javahongxi/babi)** — 深入实践 AI Coding Agent，探索 Agent 工程化落地
- **[jaws](https://github.com/javahongxi/jaws)** — 支持将 Jaws RPC 服务自动暴露为 MCP Tools

&copy; [hongxi.org](http://hongxi.org)
