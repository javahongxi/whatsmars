package org.hongxi.whatsmars.ai.retrieval;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

/**
 * 基于高级 RAG 流水线的知识库问答助手
 * <p>
 * 注意：该接口不使用 @AiService 注解，而是在 RetrievalConfig 中通过 AiServices.builder() 手动构建。
 * 这样可以精确控制 RetrievalAugmentor 只注入到这一个助手，避免污染其他 @AiService 接口。
 * </p>
 * <p>
 * 高级 RAG 流水线：
 * 查询压缩 → 智能路由（闲聊跳过检索）→ 向量检索 → LLM 重排序 → 来源标注注入
 * </p>
 *
 * @author hongxi
 */
public interface RetrievalAssistant {

    /**
     * 基于知识库回答问题（带查询压缩 + 智能路由 + 重排序 + 来源追踪）
     * <p>
     * 该接口使用自定义 RetrievalAugmentor，流程为：
     * 1. 压缩用户查询为简洁关键词
     * 2. 智能路由判断是否需要知识库检索
     * 3. 向量检索知识库
     * 4. LLM 重排序并按相关性评分排序
     * 5. 注入带来源标注的上下文
     * 6. LLM 基于增强后的消息生成回答
     * </p>
     *
     * @param userMessage 用户问题
     * @return 流式回答
     */
    @SystemMessage("""
            你是一个专业的 Java 技术助手。请严格基于检索到的知识库内容回答问题。
            如果知识库中没有相关信息，请明确说明'知识库中未找到相关信息'，不要编造答案。
            回答时请引用资料来源编号（如 [1]、[2]），让用户知道你的回答基于哪些资料。
            """)
    TokenStream chat(String userMessage);
}
