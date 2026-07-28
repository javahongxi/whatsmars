package org.hongxi.whatsmars.ai.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MCP 配置
 * <p>
 * 演示 LangChain4j 作为 MCP Client 通过 SSE 传输协议连接 whatsmars-mcp Server。
 * whatsmars-mcp Server（Spring AI）暴露了 toUpperCase、toLowerCase、reverseString 三个工具，
 * AI 可以调用这些工具来回答用户问题。
 * </p>
 * <p>
 * 前置条件：需要先启动 whatsmars-mcp 模块（端口 8886）。
 * </p>
 *
 * @author hongxi
 */
@Configuration
public class McpConfig {

    private static final Logger log = LoggerFactory.getLogger(McpConfig.class);

    /**
     * MCP Client - 通过 SSE 连接 whatsmars-mcp Server
     * <p>
     * 通过 HTTP SSE 传输协议连接本地运行的 whatsmars-mcp Server（端口 8886），
     * 服务端暴露了 toUpperCase、toLowerCase、reverseString 三个工具。
     * </p>
     */
    @Bean(destroyMethod = "close")
    public McpClient mcpClient() {
        String sseUrl = "http://localhost:8886/sse";
        log.info("MCP Client 连接 SSE 端点: {}", sseUrl);

        McpTransport transport = HttpMcpTransport.builder()
                .sseUrl(sseUrl)
                .logRequests(true)
                .logResponses(true)
                .build();

        return new DefaultMcpClient.Builder()
                .key("whatsmars-mcp")
                .transport(transport)
                .build();
    }

    /**
     * MCP Tool Provider - 将 MCP 工具适配为 LangChain4j Tool
     */
    @Bean
    public McpToolProvider mcpToolProvider(McpClient mcpClient) {
        return McpToolProvider.builder()
                .mcpClients(mcpClient)
                .build();
    }

    /**
     * MCP Assistant - 使用 MCP 工具的 AI 助手
     */
    @Bean
    public McpAssistant mcpAssistant(
            StreamingChatModel streamingChatModel,
            McpToolProvider mcpToolProvider) {
        return AiServices.builder(McpAssistant.class)
                .streamingChatModel(streamingChatModel)
                .toolProvider(mcpToolProvider)
                .build();
    }
}
