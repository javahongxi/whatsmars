package org.hongxi.whatsmars.ai.rag;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
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
 * 配置向量存储和内容检索器，用于 RAG 知识库问答
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class RagConfig {

    private static final Logger log = LoggerFactory.getLogger(RagConfig.class);

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
     * 启动时自动创建表和向量索引。
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
     * 内容检索器
     * <p>
     * 根据用户查询从向量存储中检索最相关的文档片段
     * </p>
     *
     * @param embeddingModel 嵌入模型（由 langchain4j-open-ai-spring-boot-starter 自动配置）
     * @param embeddingStore 向量存储
     * @return 内容检索器
     */
    @Bean
    public ContentRetriever contentRetriever(
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore) {
        log.info("初始化 ContentRetriever");
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.5)
                .build();
    }
}
