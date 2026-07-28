package org.hongxi.whatsmars.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfig {

    /**
     * 将所有工具统一注册到 MCP Server
     * <p>
     * 复用 tool 包下的工具类，同时用于内部 Tool Calling 和 MCP 对外暴露。
     * </p>
     */
    @Bean
    public ToolCallbackProvider mcpToolProvider(
            SystemTools systemTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(systemTools)
                .build();
    }
}
