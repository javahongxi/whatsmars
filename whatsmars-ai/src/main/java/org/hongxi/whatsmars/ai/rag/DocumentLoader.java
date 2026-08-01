package org.hongxi.whatsmars.ai.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
/**
 * 文档加载器
 * <p>
 * 应用启动时自动加载 classpath:documents/ 目录下的文档，
 * 进行分割、向量化后存入向量存储，构建知识库
 * </p>
 *
 * @author hongxi
 */
@Component
public class DocumentLoader {

    private static final Logger log = LoggerFactory.getLogger(DocumentLoader.class);

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    public DocumentLoader(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    /**
     * 启动时加载并索引 documents 目录下的所有文档
     * <p>
     * 摄入前先检查向量存储是否已有数据，避免重复摄入
     * </p>
     */
    @PostConstruct
    public void loadDocuments() {
        try {
            // 先检查向量存储是否已有数据，避免重复摄入
            Embedding probeEmbedding = embeddingModel.embed("probe").content();
            var existingResults = embeddingStore.search(EmbeddingSearchRequest.builder()
                    .queryEmbedding(probeEmbedding)
                    .maxResults(1)
                    .build());
            if (!existingResults.matches().isEmpty()) {
                log.info("向量存储已有数据，跳过文档摄入");
                return;
            }

            Path docsPath = new ClassPathResource("documents").getFile().toPath();
            log.info("从 {} 加载知识库文档...", docsPath);

            List<Document> documents = FileSystemDocumentLoader.loadDocuments(docsPath);
            log.info("加载了 {} 个文档", documents.size());

            // 使用 documentSplitter 将文档拆分为多个 TextSegment
            var splitter = DocumentSplitters.recursive(500, 50);
            var segments = splitter.splitAll(documents);
            log.info("文档拆分后共 {} 个片段", segments.size());

            // DashScope embedding 接口限制单次最多 10 条，需分批向量化
            int batchSize = 10;
            for (int i = 0; i < segments.size(); i += batchSize) {
                List<TextSegment> batch = segments.subList(i, Math.min(i + batchSize, segments.size()));
                List<Embedding> embeddings = embeddingModel.embedAll(batch).content();
                embeddingStore.addAll(embeddings, batch);
                log.info("已向量化第 {}-{} 个片段", i + 1, Math.min(i + batchSize, segments.size()));
            }

            log.info("知识库初始化完成，共 {} 个片段已存入向量存储", segments.size());
        } catch (IOException e) {
            log.error("加载知识库文档失败", e);
        }
    }
}
