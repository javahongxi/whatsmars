package org.hongxi.whatsmars.ai.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * 通用 HTTP 请求工具
 * <p>
 * LLM 无法直接访问网络，通过此工具可以调用任意 REST API。
 * 适用于接口测试、数据查询、服务调用等场景。
 * </p>
 *
 * @author hongxi
 */
@Component
public class HttpRequestTool {

    private final HttpClient httpClient;

    public HttpRequestTool(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 发送 HTTP GET 请求
     */
    @Tool(name = "http_get", value = "发送 HTTP GET 请求到指定 URL，返回响应内容。适用于查询接口、获取数据等场景。")
    public String httpGet(
            @P("完整的请求 URL") String url) {
        return doRequest("GET", url, null);
    }

    /**
     * 发送 HTTP POST 请求
     */
    @Tool(name = "http_post", value = "发送 HTTP POST 请求到指定 URL，可携带 JSON 请求体。适用于提交数据、调用写入接口等场景。")
    public String httpPost(
            @P("完整的请求 URL") String url,
            @P("JSON 格式的请求体") String body) {
        return doRequest("POST", url, body);
    }

    private String doRequest(String method, String url, String body) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30));

            HttpRequest.BodyPublisher bodyPublisher = (body != null && !body.isEmpty())
                    ? HttpRequest.BodyPublishers.ofString(body)
                    : HttpRequest.BodyPublishers.noBody();

            requestBuilder.method(method, bodyPublisher);
            if (body != null && !body.isEmpty()) {
                requestBuilder.header("Content-Type", "application/json");
            }

            HttpResponse<String> response = httpClient.send(
                    requestBuilder.build(),
                    HttpResponse.BodyHandlers.ofString());

            return String.format("HTTP %d\n\n响应体:\n%s",
                    response.statusCode(),
                    truncate(response.body(), 4000));

        } catch (Exception e) {
            return "请求失败: " + e.getMessage();
        }
    }

    private String truncate(String text, int maxLen) {
        if (text == null) return "";
        return text.length() > maxLen ? text.substring(0, maxLen) + "\n... (已截断)" : text;
    }
}
