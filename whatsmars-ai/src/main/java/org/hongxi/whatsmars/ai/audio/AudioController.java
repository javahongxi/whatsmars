package org.hongxi.whatsmars.ai.audio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 音频控制器（TTS）
 * <p>
 * 基于 DashScope CosyVoice V3 模型，将文本转为语音，返回 MP3 音频流。
 * <ul>
 *   <li>GET /ai/audio/tts — 文字转语音，返回 MP3 音频</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/audio")
public class AudioController {

    private static final Logger log = LoggerFactory.getLogger(AudioController.class);

    private final DashScopeTtsModel ttsModel;

    public AudioController(DashScopeTtsModel ttsModel) {
        this.ttsModel = ttsModel;
    }

    /**
     * 文字转语音（TTS）
     * <p>
     * 使用 DashScope CosyVoice V3 模型将文本转为语音，返回 MP3 音频流。
     * </p>
     *
     * @param text  要转换的文本
     * @param voice 语音角色（可选，如 longanyang / longanhuan）
     * @return MP3 音频
     */
    @GetMapping("/tts")
    public ResponseEntity<byte[]> textToSpeech(
            @RequestParam String text,
            @RequestParam(required = false) String voice) {

        log.info("TTS 请求: text length={}, voice={}", text.length(), voice);
        byte[] audio = ttsModel.synthesize(text, voice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
        headers.setContentLength(audio.length);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"speech.mp3\"");

        return ResponseEntity.ok().headers(headers).body(audio);
    }
}
