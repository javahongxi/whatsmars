package org.hongxi.whatsmars.ai.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClient;

import java.util.*;

/**
 * DashScope 文生图模型
 * <p>
 * 封装 DashScope 原生异步 API（wan2.7-image-pro），
 * 提供异步提交（submitAsync）和状态轮询（getTaskStatus）能力。
 * </p>
 * <p>
 * DashScope 图片生成采用异步模式：
 * <ol>
 *   <li>提交任务 → 返回 taskId</li>
 *   <li>前端轮询状态接口 → 获取图片 URL</li>
 * </ol>
 * </p>
 *
 * @author hongxi
 */
public class DashScopeImageModel {

    private static final Logger log = LoggerFactory.getLogger(DashScopeImageModel.class);

    private static final String DASHSCOPE_BASE_URL = "https://dashscope.aliyuncs.com/api/v1";
    private static final String CREATE_TASK_URL = DASHSCOPE_BASE_URL + "/services/aigc/image-generation/generation";
    private static final String TASK_STATUS_URL = DASHSCOPE_BASE_URL + "/tasks/";

    private final RestClient restClient;
    private final String apiKey;
    private final String defaultModel;

    public DashScopeImageModel(RestClient restClient, String apiKey, String defaultModel) {
        this.restClient = restClient;
        this.apiKey = apiKey;
        this.defaultModel = defaultModel;
    }

    /**
     * 异步提交图片生成任务
     *
     * @param prompt 文本描述
     * @param n      生成数量
     * @param size   尺寸（如 "1024*1024"）
     * @return DashScope 任务 ID
     */
    public String submitAsync(String prompt, int n, String size) {
        log.info("异步提交图片生成, model: {}, prompt: {}, n: {}, size: {}", defaultModel, prompt, n, size);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.set("X-DashScope-Async", "enable");

        Map<String, Object> contentItem = new LinkedHashMap<>();
        contentItem.put("text", prompt);

        Map<String, Object> message = new LinkedHashMap<>();
        message.put("role", "user");
        message.put("content", List.of(contentItem));

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", defaultModel);
        body.put("input", Map.of("messages", List.of(message)));
        body.put("parameters", Map.of("size", size, "n", n, "watermark", false));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = restClient.post()
                .uri(CREATE_TASK_URL)
                .headers(h -> {
                    h.setContentType(MediaType.APPLICATION_JSON);
                    h.setBearerAuth(apiKey);
                    h.set("X-DashScope-Async", "enable");
                })
                .body(body)
                .retrieve()
                .body(Map.class);

        Map<String, Object> output = getOutput(responseBody);
        String taskId = (String) output.get("task_id");
        if (taskId == null) {
            throw new RuntimeException("创建任务失败，未返回 task_id: " + responseBody);
        }
        log.info("任务已提交, taskId: {}", taskId);
        return taskId;
    }

    /**
     * 查询任务状态
     *
     * @param taskId DashScope 任务 ID
     * @return 任务状态（包含 status 和 urls）
     */
    public TaskStatus getTaskStatus(String taskId) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> responseBody = restClient.get()
                    .uri(TASK_STATUS_URL + taskId)
                    .headers(h -> h.setBearerAuth(apiKey))
                    .retrieve()
                    .body(Map.class);

            Map<String, Object> output = getOutput(responseBody);
            String status = (String) output.get("task_status");

            List<String> urls = "SUCCEEDED".equals(status) ? extractUrls(output) : Collections.emptyList();
            return new TaskStatus(taskId, status, urls);
        } catch (Exception e) {
            log.warn("查询任务 {} 状态失败: {}", taskId, e.getMessage());
            return new TaskStatus(taskId, "UNKNOWN", Collections.emptyList());
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> extractUrls(Map<String, Object> output) {
        List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
        if (choices == null) {
            return Collections.emptyList();
        }
        return choices.stream()
                .map(choice -> {
                    Map<String, Object> msg = (Map<String, Object>) choice.get("message");
                    if (msg == null) return null;
                    List<Map<String, String>> contentList = (List<Map<String, String>>) msg.get("content");
                    if (contentList == null) return null;
                    return contentList.stream()
                            .filter(c -> "image".equals(c.get("type")) || c.containsKey("image"))
                            .map(c -> c.get("image"))
                            .filter(Objects::nonNull)
                            .findFirst()
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getOutput(Map<String, Object> body) {
        if (body == null) throw new RuntimeException("响应为空");
        Map<String, Object> output = (Map<String, Object>) body.get("output");
        if (output == null) {
            String code = (String) body.get("code");
            String message = (String) body.get("message");
            throw new RuntimeException("DashScope 错误: " + code + " - " + message);
        }
        return output;
    }

    /**
     * DashScope 任务状态
     *
     * @param taskId 任务 ID
     * @param status 状态：PENDING / RUNNING / SUCCEEDED / FAILED / CANCELED
     * @param urls   图片 URL 列表（仅 SUCCEEDED 时非空）
     */
    public record TaskStatus(String taskId, String status, List<String> urls) {}
}
