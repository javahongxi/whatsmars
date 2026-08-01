package org.hongxi.whatsmars.ai.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.hongxi.whatsmars.ai.condition.ConditionalOnMcpStatusUp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MCP 配置
 * <p>
 * 演示 LangChain4j 作为 MCP Client 通过 Streamable HTTP 传输协议连接 whatsmars-mcp Server。
 * whatsmars-mcp Server（Spring AI）暴露了天气查询和地图服务工具，
 * AI 可以调用这些工具来回答用户问题。
 * </p>
 * <p>
 * 前置条件：需要先启动 whatsmars-mcp 模块（端口 8886）。
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMcpStatusUp
public class McpConfig {

    private static final Logger log = LoggerFactory.getLogger(McpConfig.class);

    @Value("${mcp.server.url}")
    private String mcpUrl;

    @Value("${mcp.server.key}")
    private String mcpKey;

    /**
     * MCP Client - 通过 Streamable HTTP 连接 whatsmars-mcp Server
     * <p>
     * 通过 Streamable HTTP 传输协议连接本地运行的 whatsmars-mcp Server（端口 8886），
     * 服务端暴露了天气查询（get_weather）和地图服务（show_location/plan_route/search_place）工具。
     * </p>
     */
    @Bean(destroyMethod = "close")
    public McpClient mcpClient() {
        log.info("MCP Client 连接 Streamable HTTP 端点: {}", mcpUrl);

        McpTransport transport = StreamableHttpMcpTransport.builder()
                .url(mcpUrl)
                .logRequests(true)
                .logResponses(true)
                .build();

        return new DefaultMcpClient.Builder()
                .key(mcpKey)
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
