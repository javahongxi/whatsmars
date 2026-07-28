package org.hongxi.whatsmars.ai.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Web 搜索工具（基于 Tavily Search API）
 * <p>
 * LLM 的训练数据有截止日期，无法获取实时信息（新闻、价格、事件等）。
 * 通过 Tavily API 提供实时网络搜索能力，AI 在需要时效性数据时会自动调用。
 * </p>
 * <p>
 * 需要配置 TAVILY_API_KEY 环境变量（免费额度：1000 次/月）。
 * 申请地址：https://tavily.com
 * </p>
 *
 * @author hongxi
 */
@Component
public class WebSearchTool {

    private final HttpClient httpClient;
    private final String apiKey;
    private final ObjectMapper objectMapper;

    public WebSearchTool(@Value("${TAVILY_API_KEY:}") String apiKey) {
        this.apiKey = apiKey != null && !apiKey.isEmpty() ? apiKey : System.getenv("TAVILY_API_KEY");
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 搜索网络获取实时信息
     *
     * @param query      搜索关键词
     * @param maxResults 最大结果数（1-10）
     * @return 搜索结果
     */
    @Tool(name = "web_search", value = """
            搜索网络获取实时信息。当用户询问「最近」「最新」「当前」「现在」等时效性问题时必须调用此工具，
            包括但不限于：最新电影/新闻/事件/价格/版本发布/体育赛事等。你的训练数据有截止日期，无法回答训练之后发生的事情。
            返回搜索结果的标题、摘要和链接。
            """)
    public String webSearch(
            @P("搜索关键词，尽量具体明确。如需时效性信息可加上年份，如 '2026年最新Java版本'") String query,
            @P("最大结果数量（1-10，默认 5）") Integer maxResults) {

        if (apiKey == null || apiKey.isEmpty()) {
            return """
                    Web 搜索功能未配置。请设置 TAVILY_API_KEY 环境变量。
                    申请地址：https://tavily.com（免费额度：1000 次/月）
                    """;
        }

        int results = (maxResults != null && maxResults >= 1 && maxResults <= 10) ? maxResults : 5;

        try {
            ObjectNode requestBody = objectMapper.createObjectNode()
                    .put("query", query)
                    .put("max_results", results)
                    .put("include_answer", true);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.tavily.com/search"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .timeout(Duration.ofSeconds(30))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return "搜索请求失败: HTTP " + response.statusCode();
            }

            return parseSearchResults(response.body());

        } catch (Exception e) {
            return "搜索失败: " + e.getMessage();
        }
    }

    private String parseSearchResults(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            StringBuilder sb = new StringBuilder();

            // AI 摘要
            String answer = root.path("answer").asText("");
            if (!answer.isEmpty()) {
                sb.append("【AI 摘要】\n").append(answer).append("\n\n");
            }

            // 搜索结果列表
            JsonNode results = root.path("results");
            if (results.isArray() && !results.isEmpty()) {
                sb.append("【搜索结果】\n");
                int count = 0;
                for (JsonNode item : results) {
                    if (count >= 10) break;
                    String title = textOf(item, "title");
                    if (title == null) continue;
                    sb.append(++count).append(". ").append(title).append('\n');
                    String content = textOf(item, "content");
                    if (content != null) {
                        sb.append("   ").append(truncate(content, 200)).append('\n');
                    }
                    String url = textOf(item, "url");
                    if (url != null) {
                        sb.append("   链接: ").append(url).append('\n');
                    }
                }
            }

            return sb.length() > 0 ? sb.toString() : "未找到相关搜索结果";
        } catch (Exception e) {
            return "解析搜索结果失败: " + e.getMessage();
        }
    }

    private static String textOf(JsonNode node, String field) {
        JsonNode child = node.path(field);
        return child.isMissingNode() || child.isNull() ? null : child.asText();
    }

    private static String truncate(String text, int maxLen) {
        return text.length() > maxLen ? text.substring(0, maxLen) + "..." : text;
    }
}
