package org.hongxi.whatsmars.ai.mcp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * MCP Client 服务
 * <p>
 * 通过 SSE 连接远程 MCP Server（whatsmars-ai-spring），
 * 自动发现并调用远程工具（天气、时间、计算器、搜索等）。
 * </p>
 * <p>
 * 工作流程：
 * 1. MCP Client 启动时通过 SSE 连接 MCP Server，获取所有可用工具定义
 * 2. 用户发送问题后，ChatClient 将问题 + 工具定义发送给 LLM
 * 3. LLM 判断是否需要调用工具，返回 tool_calls
 * 4. MCP Client 通过 SSE 调用 MCP Server 上对应的工具方法
 * 5. 工具执行结果返回给 LLM，生成最终回答
 * </p>
 *
 * @author hongxi
 */
@Service
public class McpClientService {

    private static final Logger log = LoggerFactory.getLogger(McpClientService.class);

    private final ChatClient chatClient;
    private final ToolCallbackProvider mcpTools;

    /**
     * @param chatClientBuilder ChatClient 构建器（由 spring-ai-starter-model-openai 自动配置）
     * @param mcpTools          MCP 远程工具（由 spring-ai-starter-mcp-client 自动从 MCP Server 发现并注入）
     */
    public McpClientService(ChatClient.Builder chatClientBuilder, ToolCallbackProvider mcpTools) {
        this.chatClient = chatClientBuilder.build();
        this.mcpTools = mcpTools;
    }

    /**
     * 同步对话 - 调用远程 MCP 工具
     *
     * @param message 用户问题
     * @return AI 回复
     */
    public String chat(String message) {
        log.info("MCP Client 收到问题: {}", message);
        String response = chatClient.prompt()
                .system("你是一个智能助手，可以通过远程 MCP 工具来获取信息并回答用户问题。请用中文回答。")
                .user(message)
                .toolCallbacks(mcpTools)
                .call()
                .content();
        log.info("MCP Client AI 回复: {}", response);
        return response;
    }

    /**
     * 流式对话 - 调用远程 MCP 工具
     *
     * @param message 用户问题
     * @return AI 流式回复
     */
    public Flux<String> chatStream(String message) {
        log.info("MCP Client 流式收到问题: {}", message);
        return chatClient.prompt()
                .system("你是一个智能助手，可以通过远程 MCP 工具来获取信息并回答用户问题。请用中文回答。")
                .user(message)
                .toolCallbacks(mcpTools)
                .stream()
                .content();
    }
}
