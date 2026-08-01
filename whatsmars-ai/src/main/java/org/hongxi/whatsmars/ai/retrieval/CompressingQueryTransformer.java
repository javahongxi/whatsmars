package org.hongxi.whatsmars.ai.retrieval;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.rag.query.transformer.QueryTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * 查询压缩转换器
 * <p>
 * 利用 LLM 将冗长或口语化的用户查询压缩为简洁的检索关键词，
 * 去除无意义的语气词和寒暄，保留核心技术术语，提升向量检索的精准度。
 * </p>
 * <p>
 * 示例：
 * <ul>
 *   <li>"你好，我想问一下 Spring Boot 有哪些核心特性？" → "Spring Boot 核心特性"</li>
 *   <li>"帮我看看 ConcurrentHashMap 到底是怎么实现线程安全的" → "ConcurrentHashMap 线程安全实现原理"</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
public class CompressingQueryTransformer implements QueryTransformer {

    private static final Logger log = LoggerFactory.getLogger(CompressingQueryTransformer.class);

    private final ChatModel chatModel;

    public CompressingQueryTransformer(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public Collection<Query> transform(Query query) {
        String original = query.text();

        // 短查询直接透传，无需压缩
        if (original.length() <= 20) {
            log.debug("查询过短，直接透传: \"{}\"", original);
            return List.of(query);
        }

        try {
            String compressed = chatModel.chat("""
                    将以下用户查询压缩为简洁的检索关键词，用于在知识库中搜索。
                    要求：只保留核心概念和技术术语，去除寒暄、语气词和冗余表达。
                    直接输出关键词，不要任何解释，不超过15个字。
                    
                    用户查询: %s
                    """.formatted(original));

            compressed = compressed.trim();
            if (compressed.isEmpty() || compressed.length() > original.length()) {
                log.debug("压缩结果无效，使用原始查询");
                return List.of(query);
            }

            log.info("查询压缩: \"{}\" → \"{}\"", original, compressed);
            return List.of(Query.from(compressed));
        } catch (Exception e) {
            log.warn("查询压缩失败，使用原始查询: {}", e.getMessage());
            return List.of(query);
        }
    }
}
