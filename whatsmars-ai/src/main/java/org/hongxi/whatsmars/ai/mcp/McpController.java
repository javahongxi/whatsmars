package org.hongxi.whatsmars.ai.mcp;

import org.hongxi.whatsmars.ai.condition.ConditionalOnMcpStatusUp;
import org.hongxi.whatsmars.ai.common.SseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * MCP 对话控制器
 * <p>
 * 演示通过 MCP 协议调用 whatsmars-mcp Server 提供的工具进行对话。
 * 调用 {@code GET /ai/mcp/chat?message=...}，流式输出。
 * </p>
 * <p>
 * AI 通过 MCP Client 连接 whatsmars-mcp Server，
 * 可以调用天气查询、地图服务等工具来回答用户问题。
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/mcp")
@ConditionalOnMcpStatusUp
public class McpController {

    private static final Logger log = LoggerFactory.getLogger(McpController.class);

    private final McpAssistant mcpAssistant;

    public McpController(McpAssistant mcpAssistant) {
        this.mcpAssistant = mcpAssistant;
    }

    /**
     * MCP 工具调用对话接口（流式输出）
     *
     * @param message 用户消息
     * @return SSE 发射器
     */
    @GetMapping(value = "/chat", produces = "text/event-stream")
    public SseEmitter chat(@RequestParam String message) {
        log.info("MCP 对话: {}", message);
        SseEmitter emitter = new SseEmitter(0L);
        SseHelper.stream(mcpAssistant.chat(message), emitter);
        return emitter;
    }
}
