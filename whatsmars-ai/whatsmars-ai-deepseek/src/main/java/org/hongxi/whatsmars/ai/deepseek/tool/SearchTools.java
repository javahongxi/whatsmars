package org.hongxi.whatsmars.ai.deepseek.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 搜索工具类
 * <p>
 * 模拟搜索引擎功能，为 AI 提供知识查询能力。
 * 在实际项目中，可以对接 Elasticsearch、向量数据库或第三方搜索 API。
 * </p>
 *
 * @author hongxi
 */
@Component
public class SearchTools {

    private static final Map<String, String> KNOWLEDGE_BASE = new ConcurrentHashMap<>();

    static {
        KNOWLEDGE_BASE.put("Spring Boot",
                "Spring Boot 是一个用于快速构建基于 Spring 框架的生产级应用程序的框架。" +
                        "它简化了 Spring 应用的初始搭建和开发过程，提供了自动配置、嵌入式服务器、起步依赖等功能。" +
                        "Spring Boot 4.x 基于 Spring Framework 7，要求 JDK 17+，支持虚拟线程和 AOT 编译。");

        KNOWLEDGE_BASE.put("Spring AI",
                "Spring AI 是 Spring 生态中的 AI 开发框架，为 Java 开发者提供统一的 AI 模型调用接口。" +
                        "2.0 版本支持 Tool Calling（@Tool 注解）、Advisors 机制、MCP 协议、结构化输出等特性。" +
                        "兼容 OpenAI、通义千问、Anthropic 等多种模型提供商。");

        KNOWLEDGE_BASE.put("Apache Dubbo",
                "Apache Dubbo 是一款高性能、轻量级的开源 Java RPC 框架。" +
                        "它提供了三大核心能力：面向接口的远程方法调用、智能容错和负载均衡、服务自动注册和发现。" +
                        "Dubbo 3.x 支持 Triple 协议，实现了与 gRPC 的互通。");

        KNOWLEDGE_BASE.put("Redis",
                "Redis 是一个开源的内存数据结构存储系统，可用作数据库、缓存和消息中间件。" +
                        "它支持多种类型的数据结构，如字符串、哈希、列表、集合、有序集合等。" +
                        "Redis 7.x 支持 Redis Functions、多活跃拓扑等新特性。");

        KNOWLEDGE_BASE.put("Kafka",
                "Apache Kafka 是一个分布式流处理平台，具有高吞吐量、低延迟的特点。" +
                        "常用于构建实时数据管道和流式应用，支持消息持久化和多订阅者模式。" +
                        "通过 Spring Cloud Stream 可以方便地与 Spring Boot 集成。");

        KNOWLEDGE_BASE.put("Nacos",
                "Nacos 是一个动态服务发现、配置管理和服务管理平台。" +
                        "它支持服务注册与发现、配置管理、服务健康监测等功能。" +
                        "是 Spring Cloud Alibaba 的核心组件之一。");

        KNOWLEDGE_BASE.put("RocketMQ",
                "Apache RocketMQ 是一个分布式消息中间件，具有高吞吐量、低延迟的特点。" +
                        "支持事务消息、顺序消息、延时消息等多种消息类型。" +
                        "通过 Spring Cloud Stream 可以方便地与 Spring Boot 集成。");

        KNOWLEDGE_BASE.put("Elasticsearch",
                "Elasticsearch 是一个分布式搜索和分析引擎，基于 Lucene 构建。" +
                        "它提供了 RESTful API，支持全文搜索、结构化搜索和分析功能。" +
                        "常用于日志分析、搜索引擎和实时数据分析场景。");
    }

    /**
     * 搜索指定主题的信息
     *
     * @param query 搜索关键词
     * @return 搜索结果描述
     */
    @Tool(description = "搜索指定技术主题的相关信息，返回简要介绍")
    public String search(@ToolParam(description = "搜索关键词，例如：Spring Boot、Apache Dubbo、Nacos、Spring AI") String query) {
        String lowerQuery = query.toLowerCase();

        // 模糊匹配知识库
        for (Map.Entry<String, String> entry : KNOWLEDGE_BASE.entrySet()) {
            if (lowerQuery.contains(entry.getKey().toLowerCase()) || entry.getKey().toLowerCase().contains(lowerQuery)) {
                return entry.getValue();
            }
        }

        return "未找到关于 \"" + query + "\" 的相关信息。已收录的主题：Spring Boot、Spring AI、Apache Dubbo、Nacos、Redis、Kafka、RocketMQ、Elasticsearch";
    }

    /**
     * 获取最新的技术资讯
     *
     * @param topic 技术主题
     * @return 最新资讯摘要
     */
    @Tool(description = "获取指定技术领域的最新发展趋势和资讯")
    public String getLatestNews(@ToolParam(description = "技术主题，例如：人工智能、微服务、云计算") String topic) {
        return switch (topic.toLowerCase()) {
            case "人工智能", "ai" -> "人工智能领域正在快速发展，大语言模型（LLM）、多模态 AI、Agent 技术成为热点。" +
                    "Spring AI 2.0 引入了 MCP 协议和 Tool Calling，Java 开发者可以更方便地构建 AI 应用。";
            case "微服务" -> "微服务架构继续演进，Service Mesh、Serverless、云原生等技术成为主流。" +
                    "Spring Cloud 2025.x 全面支持 Spring Boot 4，Dubbo 3.x 实现了与 gRPC 的互通。";
            case "云计算" -> "云计算进入多云和混合云时代，边缘计算、云原生安全、FinOps 等概念受到关注。" +
                    "Kubernetes 生态持续完善，Serverless 容器成为趋势。";
            default -> "暂无 \"" + topic + "\" 相关的最新资讯";
        };
    }
}
