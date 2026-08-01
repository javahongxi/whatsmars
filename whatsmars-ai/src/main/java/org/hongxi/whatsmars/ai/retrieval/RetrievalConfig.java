package org.hongxi.whatsmars.ai.retrieval;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.query.router.DefaultQueryRouter;
import dev.langchain4j.rag.query.transformer.ExpandingQueryTransformer;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RAG（检索增强生成）配置
 * <p>
 * 配置向量存储和自定义 RetrievalAugmentor，构建完整的 RAG 流水线：
 * <pre>
 * 用户查询
 *   ↓
 * QueryTransformer（查询扩展：1条 → 3条变体查询）
 *   ↓
 * QueryRouter（路由到 ContentRetriever 检索知识库）
 *   ↓
 * ContentAggregator（合并去重多路检索结果）
 *   ↓
 * ContentInjector（将检索内容注入到用户消息中）
 *   ↓
 * 增强后的消息 → LLM 生成回答
 * </pre>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class RetrievalConfig {

    private static final Logger log = LoggerFactory.getLogger(RetrievalConfig.class);

    @Value("${pgvector.host:localhost}")
    private String host;

    @Value("${pgvector.port:5432}")
    private int port;

    @Value("${pgvector.database:ai_demo}")
    private String database;

    @Value("${pgvector.user:ai_user}")
    private String user;

    @Value("${pgvector.password:ai_user}")
    private String password;

    @Value("${pgvector.table:langchain4j_vector_store}")
    private String table;

    @Value("${pgvector.dimension:1024}")
    private int dimension;

    /**
     * PgVector 向量存储
     * <p>
     * 使用 PostgreSQL + pgvector 扩展作为持久化向量存储，
     * 支持向量存储、相似性搜索和混合检索。
     * 启动时自动创建表，不删除已有数据以避免重复摄入。
     * </p>
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        log.info("初始化 PgVectorEmbeddingStore [{}:{}/{}]", host, port, database);
        return PgVectorEmbeddingStore.builder()
                .host(host)
                .port(port)
                .database(database)
                .user(user)
                .password(password)
                .table(table)
                .dimension(dimension)
                .createTable(true)
                .dropTableFirst(false)
                .build();
    }

    /**
     * 手动构建 RetrievalAssistant
     * <p>
     * 为什么不用 @AiService？
     * 因为 @AiService 会自动将容器中的 RetrievalAugmentor Bean 注入到所有 @AiService 接口，
     * 导致 ChatMemoryAssistant、SimpleAssistant 等也被迫走 RAG 流程。
     * </p>
     * <p>
     * 通过 AiServices.builder() 手动构建，可以精确控制：
     * - RetrievalAugmentor 只注入到 RetrievalAssistant
     * - 不暴露 RetrievalAugmentor 为独立 Bean，避免污染其他助手
     * </p>
     *
     * @param streamingChatModel 流式对话模型
     * @param chatModel          对话模型（用于查询扩展）
     * @param embeddingModel     嵌入模型（用于向量化查询）
     * @param embeddingStore     向量存储
     * @return 手动构建的 RetrievalAssistant
     */
    @Bean
    public RetrievalAssistant retrievalAssistant(
            StreamingChatModel streamingChatModel,
            ChatModel chatModel,
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore) {

        log.info("手动构建 RetrievalAssistant（含自定义 RAG 流水线）");

        // 1. 查询扩展：利用 LLM 将用户查询扩展为 3 条不同角度的变体查询
        var queryTransformer = ExpandingQueryTransformer.builder()
                .chatModel(chatModel)
                .n(3)
                .build();

        // 2. 自定义内容检索器
        var contentRetriever = new CustomContentRetriever(embeddingStore, embeddingModel, 5, 0.5);

        // 3. 查询路由器：将（多条）查询路由到检索器
        var queryRouter = new DefaultQueryRouter(contentRetriever);

        // 4. 内容注入器：将检索到的文档片段以模板格式注入到用户消息中
        var contentInjector = DefaultContentInjector.builder()
                .promptTemplate(new PromptTemplate("""
                        {{userMessage}}
                        
                        基于以下参考资料回答用户问题。
                        如果参考资料中没有相关信息，请明确说明。
                        
                        参考资料:
                        {{contents}}
                        """))
                .build();

        // 5. 构建 RetrievalAugmentor（不作为 Bean 暴露，避免污染其他 @AiService）
        var retrievalAugmentor = DefaultRetrievalAugmentor.builder()
                .queryTransformer(queryTransformer)
                .queryRouter(queryRouter)
                .contentInjector(contentInjector)
                .build();

        // 6. 手动构建 RetrievalAssistant
        return AiServices.builder(RetrievalAssistant.class)
                .streamingChatModel(streamingChatModel)
                .retrievalAugmentor(retrievalAugmentor)
                .build();
    }
}
