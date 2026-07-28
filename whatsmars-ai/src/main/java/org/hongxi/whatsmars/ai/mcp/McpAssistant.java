package org.hongxi.whatsmars.ai.mcp;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

/**
 * MCP 工具调用 AI 助手
 * <p>
 * 通过 MCP（Model Context Protocol）连接 whatsmars-mcp Server，
 * 使用其提供的工具（文本大写、小写、反转）来回答用户问题。
 * </p>
 *
 * @author hongxi
 */
public interface McpAssistant {

    @SystemMessage("""
            你是一个智能助手，可以通过 MCP 协议调用 whatsmars-mcp Server 提供的工具来完成任务。
            当前可用的工具包括：
            - toUpperCase：将英文文本转换为大写
            - toLowerCase：将英文文本转换为小写
            - reverseString：反转字符串

            重要规则：
            1. 当用户需要文本转换时，使用对应的工具处理后再回答。
            2. 不要编造结果，必须基于工具返回的实际数据回答。
            3. 用中文回答。
            """)
    TokenStream chat(String userMessage);
}
