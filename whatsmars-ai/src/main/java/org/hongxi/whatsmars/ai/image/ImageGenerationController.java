package org.hongxi.whatsmars.ai.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 图片生成控制器
 * <p>
 * 异步模式：提交任务立即返回 taskId，前端轮询状态接口获取结果。
 * <ul>
 *   <li>POST /ai/image/generate — 提交任务，返回 taskId</li>
 *   <li>GET  /ai/image/status/{taskId} — 查询任务状态和图片 URL</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/image")
public class ImageGenerationController {

    private static final Logger log = LoggerFactory.getLogger(ImageGenerationController.class);

    private final DashScopeImageModel imageModel;

    public ImageGenerationController(DashScopeImageModel imageModel) {
        this.imageModel = imageModel;
    }

    /**
     * 提交图片生成任务（异步，立即返回 taskId）
     *
     * @param prompt 文本描述
     * @param n      生成数量（可选，默认1）
     * @param size   图片尺寸（可选，默认 1024*1024）
     * @return {"taskId": "xxx"}
     */
    @PostMapping("/generate")
    public Map<String, String> generateImage(@RequestParam String prompt,
                                             @RequestParam(defaultValue = "1") Integer n,
                                             @RequestParam(defaultValue = "1024*1024") String size) {
        log.info("图片生成请求: prompt={}, n={}, size={}", prompt, n, size);
        String taskId = imageModel.submitAsync(prompt, n, size);
        return Map.of("taskId", taskId);
    }

    /**
     * 查询任务状态
     *
     * @param taskId DashScope 任务 ID
     * @return {"taskId": "xxx", "status": "SUCCEEDED", "urls": [...]}
     */
    @GetMapping("/status/{taskId}")
    public DashScopeImageModel.TaskStatus getTaskStatus(@PathVariable String taskId) {
        return imageModel.getTaskStatus(taskId);
    }
}
