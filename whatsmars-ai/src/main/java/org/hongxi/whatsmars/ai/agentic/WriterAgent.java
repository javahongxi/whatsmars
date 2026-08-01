package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 循环工作流 - 文档写作 Agent
 * <p>
 * 根据主题和（可选的）评审反馈撰写技术文档。
 * 在循环工作流中与 QualityReviewerAgent 配合，反复改进直到质量达标。
 * </p>
 *
 * @author hongxi
 */
public interface WriterAgent {

    @Agent("根据主题撰写高质量技术文档")
    @SystemMessage("""
            你是一个资深的技术文档写作专家。请根据给定主题撰写一篇结构清晰、内容准确的技术文档。
            文档应包含：标题、概述、核心内容、示例代码（如适用）、总结。
            如果有评审反馈，请认真根据反馈改进文档质量。
            不要编造事实，保持专业性和准确性。
            """)
    @UserMessage("""
            主题：{{topic}}
            评审反馈：{{feedback}}
            请根据以上信息撰写/改进技术文档。
            """)
    String writeDocument(@V("topic") String topic, @V("feedback") String feedback);
}
