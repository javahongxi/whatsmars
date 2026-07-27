package org.hongxi.whatsmars.ai.rag;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.spring.AiService;

/**
 * RAG 知识库问答助手
 * <p>
 * 通过 @AiService 注解，Spring Boot 启动时会自动扫描该接口，
 * 并自动注入 ChatLanguageModel 和 ContentRetriever（RAG 检索器）
 * </p>
 *
 * @author hongxi
 */
@AiService
public interface RagAssistant {

    /**
     * 基于知识库回答问题
     * <p>
     * AI 会先从知识库中检索与问题相关的文档片段，
     * 然后结合检索到的上下文生成回答
     * </p>
     *
     * @param userMessage 用户问题
     * @return 基于知识库的回答
     */
    @SystemMessage("你是一个专业的 Java 技术助手。请严格基于检索到的知识库内容回答问题。"
            + "如果知识库中没有相关信息，请明确说明'知识库中未找到相关信息'，不要编造答案。")
    TokenStream chat(String userMessage);
}
