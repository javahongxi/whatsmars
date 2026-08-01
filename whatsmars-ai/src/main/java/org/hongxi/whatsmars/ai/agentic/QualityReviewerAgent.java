package org.hongxi.whatsmars.ai.agentic;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 循环工作流 - 质量评审 Agent
 * <p>
 * 对技术文档进行质量评审，给出评分（0-1）和改进建议。
 * 在循环工作流中与 WriterAgent 配合，反复改进直到质量分数超过阈值。
 * </p>
 *
 * @author hongxi
 */
public interface QualityReviewerAgent {

    @Agent("评审技术文档质量，给出评分和改进建议")
    @SystemMessage("""
            你是一个严格的技术文档质量评审专家。请对给定的技术文档进行评审。
            评审维度：结构完整性、技术准确性、可读性、代码示例质量、专业术语使用。
            你必须严格以 JSON 格式回复，包含以下字段：
            - score: 0到1之间的浮点数，表示文档质量分数
            - feedback: 具体的改进建议（字符串）
            - strengths: 文档的优点（字符串）
            只返回 JSON，不要包含其他文字。
            """)
    @UserMessage("请评审以下技术文档：\n\n{{document}}")
    QualityReview reviewDocument(@V("document") String document);

    record QualityReview(double score, String feedback, String strengths) {}
}
