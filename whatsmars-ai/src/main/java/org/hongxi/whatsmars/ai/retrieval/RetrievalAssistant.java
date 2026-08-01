package org.hongxi.whatsmars.ai.retrieval;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.spring.AiService;

/**
 * 基于自定义 RetrievalAugmentor 的知识库问答助手
 * <p>
 * 与 rag 包的 RagAssistant 区别：
 * <ul>
 *   <li>RagAssistant：依赖自动发现的 ContentRetriever，底层使用默认的 DefaultRetrievalAugmentor</li>
 *   <li>RetrievalAssistant：Spring 容器中存在自定义的 RetrievalAugmentor Bean，
 *       @AiService 自动发现并使用它，从而启用查询扩展等高级 RAG 特性</li>
 * </ul>
 *
 * @author hongxi
 */
@AiService
public interface RetrievalAssistant {

    /**
     * 基于知识库回答问题（带查询扩展）
     * <p>
     * 该接口使用自定义 RetrievalAugmentor，流程为：
     * 1. 将用户查询扩展为 3 条变体查询（提升召回率）
     * 2. 对每条变体查询分别检索知识库
     * 3. 合并去重检索结果
     * 4. 将检索内容注入到用户消息中
     * 5. LLM 基于增强后的消息生成回答
     * </p>
     *
     * @param userMessage 用户问题
     * @return 流式回答
     */
    @SystemMessage("""
            你是一个专业的 Java 技术助手。请严格基于检索到的知识库内容回答问题。
            如果知识库中没有相关信息，请明确说明'知识库中未找到相关信息'，不要编造答案。
            """)
    TokenStream chat(String userMessage);
}
