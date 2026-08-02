package org.hongxi.whatsmars.ai.audio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.*;

/**
 * DashScope TTS（文字转语音）实现
 * <p>
 * 封装 DashScope 原生 HTTP API，调用 CosyVoice V3 系列模型进行语音合成。
 * DashScope 不支持 OpenAI 标准的 /v1/audio/speech 端点，
 * 因此使用 DashScope 原生 /api/v1/services/audio/tts/SpeechSynthesizer 端点。
 * </p>
 *
 * @author hongxi
 */
public class DashScopeTtsModel {

    private static final Logger log = LoggerFactory.getLogger(DashScopeTtsModel.class);

    private static final String TTS_URL = "https://dashscope.aliyuncs.com/api/v1/services/audio/tts/SpeechSynthesizer";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String apiKey;
    private final String defaultModel;
    private final String defaultVoice;

    public DashScopeTtsModel(RestClient restClient, String apiKey, String defaultModel, String defaultVoice) {
        this.restClient = restClient;
        this.objectMapper = new ObjectMapper();
        this.apiKey = apiKey;
        this.defaultModel = defaultModel;
        this.defaultVoice = defaultVoice;
    }

    /**
     * 文字转语音
     *
     * @param text  要转换的文本
     * @param voice 语音角色（可选，null 使用默认）
     * @return 音频字节数据（MP3 格式）
     */
    public byte[] synthesize(String text, String voice) {
        String model = defaultModel;
        String voiceId = (voice != null && !voice.isBlank()) ? voice : defaultVoice;

        log.info("DashScope TTS 请求, model: {}, voice: {}, text length: {}", model, voiceId, text.length());

        Map<String, Object> input = new LinkedHashMap<>();
        input.put("text", text);
        input.put("voice", voiceId);
        input.put("format", "mp3");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model", model);
        body.put("input", input);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> responseBody = restClient.post()
                    .uri(TTS_URL)
                    .headers(h -> {
                        h.setContentType(MediaType.APPLICATION_JSON);
                        h.setBearerAuth(apiKey);
                    })
                    .body(body)
                    .retrieve()
                    .body(Map.class);

            if (responseBody == null) {
                throw new RuntimeException("DashScope TTS 返回空响应");
            }

            // 解析 JSON，提取 audio url
            String json = objectMapper.writeValueAsString(responseBody);
            JsonNode root = objectMapper.readTree(json);
            JsonNode audioNode = root.path("output").path("audio");
            String audioUrl = audioNode.path("url").asText(null);

            if (audioUrl == null || audioUrl.isEmpty()) {
                // 可能直接返回了二进制数据（某些模型/版本）
                String data = audioNode.path("data").asText(null);
                if (data != null && !data.isEmpty()) {
                    byte[] audioBytes = Base64.getDecoder().decode(data);
                    log.info("TTS 合成成功(Base64), 音频大小: {} bytes", audioBytes.length);
                    return audioBytes;
                }
                throw new RuntimeException("DashScope TTS 响应中未找到音频 URL, response: " + json);
            }

            // 下载音频文件
            log.info("从 URL 下载音频: {}", audioUrl);
            byte[] audio = restClient.get()
                    .uri(URI.create(audioUrl))
                    .retrieve()
                    .body(byte[].class);

            if (audio == null || audio.length == 0) {
                throw new RuntimeException("下载的音频数据为空");
            }

            log.info("TTS 合成成功, 音频大小: {} bytes", audio.length);
            return audio;

        } catch (Exception e) {
            log.error("DashScope TTS 调用失败: {}", e.getMessage());
            throw new RuntimeException("语音合成失败: " + e.getMessage(), e);
        }
    }
}
