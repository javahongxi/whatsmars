package org.hongxi.whatsmars.ai.deepseek.controller;

import org.hongxi.whatsmars.ai.deepseek.tool.SearchTools;
import org.hongxi.whatsmars.ai.deepseek.tool.TimeTools;
import org.hongxi.whatsmars.ai.deepseek.tool.WeatherTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/deepseek")
public class DeepSeekController {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekController.class);

    private final ChatClient chatClient;
    private final WeatherTools weatherTools;
    private final TimeTools timeTools;
    private final SearchTools searchTools;

    public DeepSeekController(ChatClient.Builder builder,
                              WeatherTools weatherTools,
                              TimeTools timeTools,
                              SearchTools searchTools) {
        this.chatClient = builder.build();
        this.weatherTools = weatherTools;
        this.timeTools = timeTools;
        this.searchTools = searchTools;
    }

    /**
     * 简单聊天接口
     */
    @RequestMapping("/chat")
    public String chat(@RequestParam String message) {
        log.info("收到聊天请求: {}", message);
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    /**
     * 流式聊天接口（SSE）
     */
    @RequestMapping("/chat/stream")
    public ResponseEntity<Flux<String>> chatStream(@RequestParam String message) {
        log.info("开始流式对话: {}", message);
        Flux<String> flux = chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .doOnNext(chunk -> log.debug("收到 chunk: {}", chunk))
                .doOnComplete(() -> log.info("流式对话完成"));
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("text/event-stream;charset=UTF-8"))
                .header("Cache-Control", "no-cache")
                .body(flux);
    }

    /**
     * 使用 System Message 设定 AI 角色
     *
     * @param message 用户消息
     * @return AI 回复
     */
    @RequestMapping("/system-message")
    public String chatWithSystemMessage(@RequestParam String message) {
        log.info("System Message 对话: {}", message);
        String response = chatClient.prompt()
                .system("你是一个资深的 Java 架构师，擅长设计高并发、高可用的分布式系统。回答要专业、深入。")
                .options(OpenAiChatOptions.builder().temperature(0.4).build()) // 低温度=更准确、更快回答
                .user(message)
                .call()
                .content();
        log.info("AI 回复: {}", response);
        return response;
    }

    /**
     * 带温度参数的创意性对话
     *
     * @param message 用户消息
     * @return AI 回复
     */
    @RequestMapping("/creative")
    public String creativeChat(@RequestParam String message) {
        log.info("创意性对话: {}", message);
        String response = chatClient.prompt()
                .system("你是一个富有创造力的作家，擅长写故事和诗歌。")
                .options(OpenAiChatOptions.builder().temperature(0.9).build()) // 高温度=更有创造力
                .user(message)
                .call()
                .content();
        log.info("AI 回复: {}", response);
        return response;
    }

    /**
     * ReAct Agent 智能问答
     * <p>
     * Agent 会自动判断需要调用哪些工具来回答问题，并可以进行多步推理。
     * </p>
     * <p>
     * 测试示例：
     * - "北京今天的天气怎么样？适合出门吗？"
     * - "什么是 Apache Dubbo？它的最新版本支持什么协议？"
     * - "现在是几号？距离春节还有多少天？"
     * - "我想了解 Spring AI 的最新发展趋势"
     * </p>
     *
     * @param message 用户消息
     * @return Agent 的回答
     */
    @RequestMapping("/agent/chat")
    public String agentChat(@RequestParam String message) {
        log.info("Agent 收到问题: {}", message);
        String response = chatClient.prompt()
                .system("""
                        你是一个智能助手，可以使用各种工具来帮助用户解决问题。
                        
                        你可以使用的工具包括：
                        - 天气查询：获取城市当前天气和天气预报
                        - 时间查询：获取当前日期、时间，计算日期差
                        - 知识搜索：搜索技术主题的相关信息
                        - 最新资讯：获取技术领域的最新动态
                        
                        回答要求：
                        1. 根据问题需要，主动调用合适的工具获取信息
                        2. 基于工具返回的结果给出完整、有用的回答
                        3. 如果一个问题需要多个工具配合，依次调用
                        4. 保持回答简洁、准确、有用
                        """)
                .user(message)
                .tools(weatherTools, timeTools, searchTools)
                .call()
                .content();
        log.info("Agent 回复: {}", response);
        return response;
    }

    /**
     * 通过 URL 分析图片
     *
     * @param imageUrl 图片 URL
     * @param prompt   提示词（可选）
     * @return 图片描述
     */
    @PostMapping("/vision/analyze-url")
    public String analyzeImageByUrl(@RequestParam String imageUrl,
                                    @RequestParam(defaultValue = "请详细描述这张图片的内容") String prompt) {
        log.info("分析图片 URL: {}", imageUrl);

        try {
            Resource imageResource = new UrlResource(imageUrl);

            String description = chatClient.prompt()
                    .options(OpenAiChatOptions.builder().model("deepseek-v4-flash").build())
                    .user(userSpec -> userSpec
                            .text(prompt)
                            .media(MediaType.IMAGE_JPEG, imageResource))
                    .call()
                    .content();

            log.info("图片描述: {}", description);
            return description;
        } catch (Exception e) {
            log.error("分析图片失败", e);
            throw new RuntimeException("分析图片失败: " + e.getMessage(), e);
        }
    }
}
