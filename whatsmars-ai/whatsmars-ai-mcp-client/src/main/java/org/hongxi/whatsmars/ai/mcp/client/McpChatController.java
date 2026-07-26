package org.hongxi.whatsmars.ai.mcp.client;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * MCP Client 控制器
 * <p>
 * 提供 HTTP 接口，通过 MCP 协议调用远程 whats-spring 模块暴露的工具。
 * 支持同步和流式两种响应方式。
 * </p>
 * <p>
 * 测试示例：
 * <ul>
 *   <li>GET /mcp/chat?message=北京今天天气怎么样（触发远程 WeatherTools）</li>
 *   <li>GET /mcp/chat?message=现在几点了（触发远程 TimeTools）</li>
 *   <li>GET /mcp/chat?message=张三的邮箱是什么（触发远程 UserTools）</li>
 *   <li>GET /mcp/chat?message=帮我算一下 123.45 * 67.89（触发远程 CalculatorTools）</li>
 *   <li>GET /mcp/chat?message=介绍一下 Spring AI（触发远程 SearchTools）</li>
 *   <li>GET /mcp/chat?message=把 hello world 转成大写（触发远程 SystemTools）</li>
 *   <li>GET /mcp/chat?message=把你好进行Base64编码（触发远程 ConversionTools）</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/mcp")
public class McpChatController {

    private final McpClientService mcpClientService;

    public McpChatController(McpClientService mcpClientService) {
        this.mcpClientService = mcpClientService;
    }

    /**
     * 同步对话 - 调用远程 MCP 工具
     *
     * @param message 用户问题
     * @return AI 回复
     */
    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam String message) {
        return ResponseEntity.ok(mcpClientService.chat(message));
    }

    /**
     * 流式对话 - 调用远程 MCP 工具（SSE）
     *
     * @param message 用户问题
     * @return AI 流式回复
     */
    @GetMapping("/chat/stream")
    public ResponseEntity<Flux<String>> chatStream(@RequestParam String message) {
        Flux<String> stream = mcpClientService.chatStream(message);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("text/event-stream;charset=UTF-8"))
                .header("Cache-Control", "no-cache")
                .body(stream);
    }
}
