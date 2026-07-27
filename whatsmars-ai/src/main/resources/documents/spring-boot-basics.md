# Spring Boot 核心知识

## 什么是 Spring Boot

Spring Boot 是由 Pivotal 团队提供的基于 Spring 框架的快速开发脚手架。它通过"约定优于配置"的理念，大幅简化了 Spring 应用的初始搭建和开发过程。

## 核心特性

### 1. 自动配置（Auto-Configuration）
Spring Boot 能够根据项目中引入的依赖，自动推断并配置应用程序所需的 Bean。例如：
- 引入 `spring-boot-starter-web` 会自动配置 Tomcat、Spring MVC、Jackson 等
- 引入 `spring-boot-starter-data-redis` 会自动配置 Redis 连接工厂和 RedisTemplate

### 2. 起步依赖（Starter）
Starter 是一组便捷的依赖描述符，将相关依赖打包在一起：
- `spring-boot-starter-web`：Web 开发（Spring MVC + Tomcat）
- `spring-boot-starter-data-jpa`：JPA 数据访问
- `spring-boot-starter-security`：安全框架集成
- `spring-boot-starter-actuator`：生产级监控和管理

### 3. 内嵌服务器
Spring Boot 默认内嵌 Tomcat/Jetty/Undertow，无需部署外部 Web 服务器，应用以 JAR 包形式独立运行。

### 4. Actuator 监控
Spring Boot Actuator 提供生产就绪的监控端点：
- `/actuator/health`：应用健康状态
- `/actuator/info`：应用自定义信息
- `/actuator/metrics`：性能指标（JVM、HTTP 请求等）

## 配置文件

Spring Boot 支持两种配置格式：
- `application.properties`：键值对格式
- `application.yml`：YAML 层级格式（推荐）

配置优先级：命令行参数 > 环境变量 > 配置文件 > 默认值

## 常用注解

| 注解 | 说明 |
|------|------|
| `@SpringBootApplication` | 组合注解，包含 `@Configuration`、`@EnableAutoConfiguration`、`@ComponentScan` |
| `@RestController` | `@Controller` + `@ResponseBody`，用于 REST API |
| `@Service` | 标记业务逻辑层组件 |
| `@Repository` | 标记数据访问层组件 |
| `@Configuration` | 标记配置类，替代 XML 配置 |
| `@Bean` | 在配置类中声明 Bean |
| `@Autowired` | 自动注入依赖 |
| `@Value` | 注入配置属性值 |

## Spring Boot 启动流程

1. 创建 SpringApplication 对象
2. 推断应用类型（Servlet/Reactive）
3. 加载 ApplicationContextInitializer
4. 加载 ApplicationListener
5. 运行 CommandLineRunner 和 ApplicationRunner
6. 刷新容器，触发自动配置
