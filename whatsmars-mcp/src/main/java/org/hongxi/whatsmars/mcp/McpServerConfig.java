package org.hongxi.whatsmars.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class McpServerConfig {

    @Bean
    public ToolCallbackProvider mcpToolProvider(
            WeatherTool weatherTool,
            MapTool mapTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherTool, mapTool)
                .build();
    }
}
