package org.hongxi.whatsmars.ai.vision;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 视觉理解控制器
 * <p>
 * 使用多模态模型理解图片内容
 * 调用 <code>POST /ai/vision/analyze</code>，传入图片 URL 和提示词，返回 AI 对图片的理解。
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/vision")
public class VisionController {

    private static final Logger log = LoggerFactory.getLogger(VisionController.class);

    private final ChatModel chatModel;

    public VisionController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 分析图片
     *
     * @param imageUrl 图片 URL
     * @param prompt   提示词（可选，默认"请详细描述这张图片的内容"）
     * @return AI 对图片的描述/分析
     */
    @PostMapping("/analyze")
    public String analyzeImage(@RequestParam String imageUrl,
                               @RequestParam(defaultValue = "请详细描述这张图片的内容") String prompt) {
        log.info("视觉理解: imageUrl={}, prompt={}", imageUrl, prompt);

        UserMessage userMessage = UserMessage.from(
                TextContent.from(prompt),
                ImageContent.from(imageUrl)
        );

        ChatResponse response = chatModel.chat(userMessage);
        String result = response.aiMessage().text();
        log.info("视觉理解结果: {}", result);
        return result;
    }
}
