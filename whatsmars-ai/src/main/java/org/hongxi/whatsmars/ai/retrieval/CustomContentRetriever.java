package org.hongxi.whatsmars.ai.retrieval;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 自定义内容检索器
 * <p>
 * 演示 ContentRetriever 的自定义实现，相比默认的 EmbeddingStoreContentRetriever，
 * 这里增加了查询日志、分数过滤等定制逻辑。
 * </p>
 * <p>
 * ContentRetriever 是 RetrievalAugmentor 的核心组件之一，
 * 负责根据用户查询从知识库中检索相关内容。
 * </p>
 *
 * @author hongxi
 */
public class CustomContentRetriever implements ContentRetriever {

    private static final Logger log = LoggerFactory.getLogger(CustomContentRetriever.class);

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;
    private final int maxResults;
    private final double minScore;

    public CustomContentRetriever(EmbeddingStore<TextSegment> embeddingStore,
                                  EmbeddingModel embeddingModel,
                                  int maxResults,
                                  double minScore) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
        this.maxResults = maxResults;
        this.minScore = minScore;
    }

    @Override
    public List<Content> retrieve(Query query) {
        log.info("检索查询: \"{}\"", query.text());

        // 1. 将查询文本转为向量
        Embedding queryEmbedding = embeddingModel.embed(query.text()).content();

        // 2. 从向量存储中检索最相似的文档片段
        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(maxResults)
                .minScore(minScore)
                .build();
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(searchRequest);
        List<EmbeddingMatch<TextSegment>> matches = searchResult.matches();

        log.info("检索到 {} 个相关片段（最高相似度: {}）",
                matches.size(),
                matches.isEmpty() ? "N/A" : String.format("%.4f", matches.get(0).score()));

        // 3. 将匹配结果转为 Content 列表
        return matches.stream()
                .map(match -> {
                    log.debug("  - [score={}] {}", String.format("%.4f", match.score()),
                            match.embedded().text().substring(0, Math.min(80, match.embedded().text().length())));
                    return Content.from(match.embedded());
                })
                .toList();
    }
}
