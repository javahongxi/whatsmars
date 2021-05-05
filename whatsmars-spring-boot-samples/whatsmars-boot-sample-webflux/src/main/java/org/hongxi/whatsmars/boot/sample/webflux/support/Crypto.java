package org.hongxi.whatsmars.boot.sample.webflux.support;

import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.Map;

/**
 * Created by shenhongxi on 2021/4/29.
 */
public class Crypto {

    /**
     * 模拟解密逻辑：添加一个请求参数 start
     * @param requestBody
     * @return
     */
    public static Map<String, Object> decrypt(Map<String, Object> requestBody) {
        requestBody.put("start", System.currentTimeMillis());
        return requestBody;
    }

    /**
     * 模拟解密逻辑：添加一个请求参数 start
     * @param formData
     * @return
     */
    public static MultiValueMap<String, String> decrypt(MultiValueMap<String, String> formData) {
        formData.put("start", Collections.singletonList(String.valueOf(System.currentTimeMillis())));
        return formData;
    }

    /**
     * 模拟加密逻辑：添加一个响应参数 end
     * @param responseBody
     * @return
     */
    public static byte[] encrypt(byte[] responseBody) {
        Map<String, Object> result = JacksonUtils.deserialize(responseBody, Map.class);
        result.put("end", System.currentTimeMillis());
        return JacksonUtils.serialize(result);
    }
}
