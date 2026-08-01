package org.hongxi.whatsmars.ai.retrieval;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.rag.query.router.QueryRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * 智能查询路由器
 * <p>
 * 利用 LLM 判断用户查询是否需要走知识库检索：
 * <ul>
 *   <li>知识类问题（技术概念、原理、用法等）→ 路由到知识库检索器</li>
 *   <li>闲聊/问候/无关问题 → 跳过检索，直接由 LLM 回答</li>
 * </ul>
 * 仅在原始查询上做一次分类决策，所有扩展查询共享同一决策，避免多次 LLM 调用。
 * </p>
 *
 * @author hongxi
 */
public class SmartQueryRouter implements QueryRouter {

    private static final Logger log = LoggerFactory.getLogger(SmartQueryRouter.class);

    private final ChatModel chatModel;
    private final ContentRetriever knowledgeRetriever;

    public SmartQueryRouter(ChatModel chatModel, ContentRetriever knowledgeRetriever) {
        this.chatModel = chatModel;
        this.knowledgeRetriever = knowledgeRetriever;
    }

    @Override
    public Collection<ContentRetriever> route(Query query) {
        if (query == null) {
            return List.of();
        }

        boolean needsKnowledge = classify(query.text());

        if (needsKnowledge) {
            log.info("查询路由 → KNOWLEDGE: \"{}\"", query.text());
            return List.of(knowledgeRetriever);
        } else {
            log.info("查询路由 → CHAT (跳过检索): \"{}\"", query.text());
            return List.of();
        }
    }

    /**
     * 使用 LLM 分类查询意图
     */
    private boolean classify(String query) {
        try {
            String response = chatModel.chat("""
                    判断以下用户查询是否需要从知识库检索资料来回答。
                    
                    如果查询涉及技术知识、概念解释、原理说明、最佳实践、框架用法等，回复 KNOWLEDGE
                    如果是问候、闲聊、与知识库无关的通用问题，回复 CHAT
                    
                    只回复 KNOWLEDGE 或 CHAT，不要其他内容。
                    
                    用户查询: %s
                    """.formatted(query));

            boolean result = response != null && response.trim().toUpperCase().contains("KNOWLEDGE");
            log.debug("LLM 分类结果: {} → {}", query, result ? "KNOWLEDGE" : "CHAT");
            return result;
        } catch (Exception e) {
            log.warn("查询分类失败，默认走知识库检索: {}", e.getMessage());
            return true; // 失败时保守策略：走检索
        }
    }
}
