## AI 项目矩阵

按技术方向拆分为三个独立项目，各司其职：

| 项目             | 聚焦方向        | 核心框架               | 语言          |
|------------------|-----------------|------------------------|---------------|
| **whatsmars-ai** | AI 框架集成     | **LangChain4j**        | Java          |
| **spacecloud**   | AI + 微服务     | **Spring AI 2.0**      | Java          |
| **babi 系列**    | AI Coding Agent | AgentScope / LangGraph | Java / Python |

- **whatsmars-ai** — 聚焦 LangChain4j 生态，深度探索其原理和应用
- **spacecloud** — 聚焦 Spring AI 2.0 在微服务场景的应用
- **babi 系列** — 面向开发者的 AI Coding Agent，实践 Agent 架构模式

## Agentic Patterns API

提供 5 种 Agent 编排模式的 REST API，基于 `langchain4j-agentic` 框架。

### 1. 基础 Agent（单 Agent + 工具调用）

Agent 自动调用 `web_search` 工具获取实时信息，生成研究报告。

```bash
curl -X POST http://localhost:8887/ai/agentic/basic \
  -H "Content-Type: application/json" \
  -d '{"topic": "Java 21虚拟线程的最新发展和最佳实践"}'
```

### 2. 顺序工作流（研究 → 摘要 → 翻译）

三个 Agent 依次执行，每个 Agent 的输出通过 `AgenticScope` 传递给下一个。

```bash
curl -X POST http://localhost:8887/ai/agentic/sequential \
  -H "Content-Type: application/json" \
  -d '{"topic": "Spring Boot 4.x 新特性"}'
```

### 3. 循环工作流（写作 → 评审 → 达标退出）

WriterAgent 和 QualityReviewerAgent 循环执行，评审分数 ≥ 0.7 或达到 3 次迭代时退出。

```bash
curl -X POST http://localhost:8887/ai/agentic/loop \
  -H "Content-Type: application/json" \
  -d '{"topic": "Redis分布式锁的正确实现方式"}'
```

### 4. 并行工作流（安全 + 性能 + 最佳实践 三路并行审查）

三个审查 Agent 并行执行，最终聚合为一份综合审查报告。

```bash
curl -X POST http://localhost:8887/ai/agentic/parallel \
  -H "Content-Type: application/json" \
  -d '{"code": "public class UserController {\n    @GetMapping(\"/user/{id}\")\n    public User getUser(@PathVariable String id) {\n        String sql = \"SELECT * FROM users WHERE id = \" + id;\n        return jdbcTemplate.queryForObject(sql, User.class);\n    }\n}"}'
```

### 5. 监督者编排（LLM 自主调度专家 Agent）

Supervisor 根据用户问题动态决定调用哪些专家、以什么顺序调用，与确定性工作流不同。

```bash
curl -X POST http://localhost:8887/ai/agentic/supervisor \
  -H "Content-Type: application/json" \
  -d '{"request": "请帮我全面审查这段代码的质量，包括安全性、性能和最佳实践：\npublic void processOrder(String orderId) {\n    Order order = orderRepo.findById(orderId);\n    order.setStatus(\"processed\");\n    orderRepo.save(order);\n}"}'
```

## 流式 SSE 端点

基础 Agent、顺序工作流、并行工作流同时提供 SSE 流式端点，中间步骤同步执行，最终结果流式输出。
循环工作流的流式端点发送每轮迭代的进度事件，最终发送完整文档。
监督者编排暂不支持流式。

### 基础 Agent（流式）

```bash
curl -N -X POST http://localhost:8887/ai/agentic/basic/stream \
  -H "Content-Type: application/json" \
  -d '{"topic": "Java 21虚拟线程的最新发展和最佳实践"}'
```

### 顺序工作流（流式）

中间步骤（研究、摘要）同步执行，最后一步（翻译）流式返回。

```bash
curl -N -X POST http://localhost:8887/ai/agentic/sequential/stream \
  -H "Content-Type: application/json" \
  -d '{"topic": "Spring Boot 4.x 新特性"}'
```

### 循环工作流（SSE 进度事件）

每轮迭代发送 `iteration` 和 `review` 事件，最终发送 `document` 事件。

```bash
curl -N -X POST http://localhost:8887/ai/agentic/loop/stream \
  -H "Content-Type: application/json" \
  -d '{"topic": "Redis分布式锁的正确实现方式"}'
```

SSE 事件格式：

```
event: iteration
data: {"iteration":1,"status":"writing"}

event: iteration
data: {"iteration":1,"status":"reviewing"}

event: review
data: {"score":0.6,"feedback":"建议增加异常处理示例"}

event: iteration
data: {"iteration":2,"status":"writing"}
...
event: document
data: 最终文档内容
```

### 并行工作流（流式）

三路并行审查完成后，最终聚合报告流式输出。

```bash
curl -N -X POST http://localhost:8887/ai/agentic/parallel/stream \
  -H "Content-Type: application/json" \
  -d '{"code": "public class UserController {\n    @GetMapping(\"/user/{id}\")\n    public User getUser(@PathVariable String id) {\n        String sql = \"SELECT * FROM users WHERE id = \" + id;\n        return jdbcTemplate.queryForObject(sql, User.class);\n    }\n}"}'
```