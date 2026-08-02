package org.hongxi.whatsmars.ai.agentic.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 条件工作流 - 分类路由 Agent
 * <p>
 * 分析用户请求并将其分类为医疗、法律、技术或未知类别。
 * 分类结果写入 AgenticScope 的 "category" 变量，供条件路由判断使用。
 * </p>
 *
 * @author hongxi
 */
public interface CategoryRouterAgent {

    /**
     * 请求分类枚举
     * <p>
     * 用于条件路由 Agent 对用户请求进行分类，决定分发给哪个专家 Agent 处理。
     * </p>
     */
    enum RequestCategory {
        MEDICAL,
        LEGAL,
        TECHNICAL,
        UNKNOWN
    }

    @UserMessage("""
            分析以下用户请求，将其分类为 'legal'、'medical' 或 'technical'。
            如果请求不属于以上任何类别，则分类为 'unknown'。
            仅回复分类单词，不要回复其他内容。
            用户请求：{{request}}
            """)
    @Agent(description = "对用户请求进行分类", outputKey = "category")
    RequestCategory classify(@V("request") String request);
}
