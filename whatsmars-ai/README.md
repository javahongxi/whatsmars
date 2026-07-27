## AI 集成的"双轨策略"
> Spring AI 更多高级功能，请访问 https://github.com/javahongxi/spring-cloud-samples

### Spring AI 核心功能（whatsmars-ai-spring）

> 本项目基于 Spring AI 1.1.x，使用阿里通义千问（Qwen）模型。
>
> **大部分接口已改为流式响应（SSE）**，浏览器访问 `http://localhost:8083` 即可体验。

#### Web 演示页面

启动后访问 [http://localhost:8083](http://localhost:8083)，页面包含以下 Tab：

基础对话 | 流式对话 | System消息 | Few-Shot | 创意对话 | 工具调用 | ReAct Agent | RAG 问答 | 对话记忆 | 结构化输出

---

#### 1. 基础聊天

```bash
curl "http://localhost:8083/ai/chat?message=你好"
curl "http://localhost:8083/ai/chat/stream?message=介绍一下武汉"
```

---

#### 2. 高级对话特性（流式）

System Message、Few-Shot、创意对话等接口均已改为 **流式 SSE** 输出。

| 接口                                 | 说明                      |
|------------------------------------|-------------------------|
| `POST /ai/advanced/system-message` | System Message 角色设定（流式） |
| `POST /ai/advanced/few-shot`       | Few-Shot 示例引导（流式）       |
| `POST /ai/advanced/creative`       | 创意性对话（流式）               |
| `POST /ai/advanced/conversation`   | 手动维护多轮上下文（流式）           |

---

#### 3. 结构化输出

```bash
curl -X POST "http://localhost:8083/ai/structured/extract-user" \
  -d "text=我叫张三，今年25岁，软件工程师，喜欢编程和篮球"

curl -X POST "http://localhost:8083/ai/structured/review-summary" \
  -d "review=小米17 Pro搭载骁龙8至尊版与徕卡影像，拍照出色，但待机功耗偏高"
```

---

#### 4. 工具调用 (Tool Calling)（流式）

通过 `@Tool` 注解让 AI 自动调用 Java 方法，**所有工具接口均为流式 SSE**。

**可用工具类：** `WeatherTools`（天气）、`TimeTools`（时间）、`SearchTools`（搜索）、`CalculatorTools`（计算）、`SystemTools`（字符串处理）、`ConversionTools`（URL/Base64 编码）

| 接口                     | 说明              |
|------------------------|-----------------|
| `GET /ai/tool/weather` | 天气查询（流式）        |
| `GET /ai/tool/time`    | 时间查询（流式）        |
| `GET /ai/tool/ask`     | 智能助手，自动选择工具（流式） |

```bash
curl "http://localhost:8083/ai/tool/weather?message=北京今天天气怎么样？"
curl "http://localhost:8083/ai/tool/ask?message=上海天气怎么样，顺便告诉我现在几点"
```

---

#### 5. ReAct Agent（流式）

Agent 自主决定调用哪些工具，支持多步推理，**流式输出**。

| 接口                                 | 说明             |
|------------------------------------|----------------|
| `GET /ai/react-agent/chat`         | Agent 智能问答（流式） |
| `GET /ai/react-agent/complex-task` | 复杂任务多步推理（流式）   |

```bash
curl "http://localhost:8083/ai/react-agent/chat?message=北京天气怎么样？适合出门吗？"
```

---

#### 6. ChatMemory 多轮对话记忆（流式）

基于 `spring-ai-starter-model-chat-memory-repository-jdbc`，对话历史持久化到 PostgreSQL，支持会话隔离。

| 接口                                   | 说明               |
|--------------------------------------|------------------|
| `GET /ai/memory/chat`                | 带记忆的多轮对话（**流式**） |
| `POST /ai/memory/chat`               | 带记忆的多轮对话（非流式，兼容） |
| `DELETE /ai/memory/{conversationId}` | 清除会话记忆           |

```shell
# 第 1 轮：告诉 AI 你的名字
curl -X GET "http://localhost:8083/ai/memory/chat?conversationId=session-001&message=你好，我叫小明"

# 第 2 轮：追问，AI 会记住上下文
curl -X GET "http://localhost:8083/ai/memory/chat?conversationId=session-001&message=我叫什么名字？"

# 不同会话完全隔离
curl -X GET "http://localhost:8083/ai/memory/chat?conversationId=session-002&message=我叫什么名字？"

# 清除会话记忆
curl -X DELETE http://localhost:8083/ai/memory/session-001
```

---

#### 7. RAG（检索增强生成）（流式）

前置条件：PostgreSQL + pgvector
```shell
brew install postgresql
brew install pgvector
# 初始化数据库（创建用户、数据库、启用 pgvector 扩展、建表）
psql -U postgres -f whatsmars-ai/init_ai_demo.sql
```

| 接口                         | 说明                    |
|----------------------------|-----------------------|
| `POST /ai/rag/ingest`      | 摄入文档到向量数据库            |
| `GET /ai/rag/query`        | 基于知识库的 RAG 问答（**流式**） |
| `DELETE /ai/rag/documents` | 删除指定来源的文档             |

```shell
# 摄入文档
curl -X POST http://localhost:8083/ai/rag/ingest \
  -H "Content-Type: application/json" \
  -d '{"content":"Spring AI is a framework for building AI-native applications...","source":"spring-ai-docs"}'

# RAG 查询（流式，topK 控制检索文档数量，默认 3）
curl --get --data-urlencode "question=What is Spring AI?" "http://localhost:8083/ai/rag/query?topK=3"
```

> 完整 RAG 流程：文档摄入 → TokenTextSplitter 自动分块 → PgVector 向量化存储 → 相似性检索 → 上下文增强 Prompt → LLM 流式生成。当知识库无相关文档时自动降级为纯 LLM 回答。

---

### LangChain4j 功能（whatsmars-ai-langchain4j）

> 基于 LangChain4j + Spring Boot Starter，使用阿里通义千问。
>
> **所有对话接口均支持流式响应（SSE）**，浏览器访问 `http://localhost:8082` 即可体验。

#### Web 演示页面

启动后访问 [http://localhost:8082](http://localhost:8082)，页面包含以下 Tab：

基础对话 | 流式对话 | 函数调用 | RAG 问答 | 对话记忆

---

#### 1. 流式响应 (TokenStream + SSE)

| 接口                         | 说明            |
|----------------------------|---------------|
| `GET /ai/chat`             | 基础聊天（非流式）     |
| `GET /ai/stream/chat`      | 流式对话（SSE）     |
| `GET /ai/stream/chat-json` | 流式对话（JSON 格式） |

#### 2. 函数调用 (@Tool)（流式）

AI 自动识别并调用 `@Tool` 标记的方法，**流式输出回答**。

| 接口                      | 说明           |
|-------------------------|--------------|
| `GET /ai/function/chat` | 函数调用（流式 SSE） |

```bash
curl "http://localhost:8082/ai/function/chat?message=现在几点了？"
curl "http://localhost:8082/ai/function/chat?message=北京天气怎么样？"
curl "http://localhost:8082/ai/function/chat?message=计算 123 + 456"
```

#### 3. RAG 知识库问答（流式）

前置条件：PostgreSQL + pgvector
```shell
brew install postgresql
brew install pgvector
# 初始化数据库（创建用户、数据库、启用 pgvector 扩展、建表）
psql -U postgres -f whatsmars-ai/init_ai_demo.sql
```

基于 LangChain4j 的 RAG 流程：文档加载 → 向量化 → 检索增强 → LLM 流式生成。

知识库文档包含 `spring-boot-basics.md` 和 `java-concurrency.md`，应用启动时自动加载。

| 接口                 | 说明                |
|--------------------|-------------------|
| `GET /ai/rag/chat` | RAG 知识库问答（流式 SSE） |

```bash
curl "http://localhost:8082/ai/rag/chat?message=Spring Boot 有哪些核心特性？"
curl "http://localhost:8082/ai/rag/chat?message=什么是 ConcurrentHashMap？"
curl "http://localhost:8082/ai/rag/chat?message=阿里巴巴规范为什么不推荐用 Executors？"
```

#### 4. ChatMemory 多轮对话记忆（流式）

基于 `@MemoryId` 实现会话隔离的多轮对话，**流式输出**。

| 接口                    | 说明               |
|-----------------------|------------------|
| `GET /ai/memory/chat` | 带记忆的多轮对话（流式 SSE） |

```shell
# 第 1 轮：告诉 AI 你的名字
curl "http://localhost:8082/ai/memory/chat?sessionId=user1&message=你好，我叫张三"

# 第 2 轮：追问，AI 会记住上下文
curl "http://localhost:8082/ai/memory/chat?sessionId=user1&message=我叫什么名字？"

# 不同会话完全隔离
curl "http://localhost:8082/ai/memory/chat?sessionId=user2&message=我叫什么名字？"
```

