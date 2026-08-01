package org.hongxi.whatsmars.ai.retrieval;

/**
 * RAG 检索来源信息
 * <p>
 * 记录每条被引用的知识库片段，用于在回答末尾展示来源，增强可信度。
 * </p>
 *
 * @param text         片段文本（截取前 200 字符）
 * @param fileName     来源文件名（可为 null）
 * @param score        向量检索相似度分数
 * @param reRankScore  LLM 重排序分数（0-10）
 * @author hongxi
 */
public record SourceInfo(
        String text,
        String fileName,
        double score,
        int reRankScore
) {
}
