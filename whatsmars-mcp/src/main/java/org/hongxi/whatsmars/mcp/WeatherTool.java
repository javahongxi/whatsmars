package org.hongxi.whatsmars.mcp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * 天气查询工具（基于 Open-Meteo API）
 * <p>
 * 完全免费，无需 API Key，支持全球任意城市实时天气和未来 3 天预报。
 * 数据来源：ECMWF、NOAA GFS 等权威气象模型。
 * </p>
 *
 * @see <a href="https://open-meteo.com/">Open-Meteo</a>
 */
@Component
public class WeatherTool {

    private static final Logger log = LoggerFactory.getLogger(WeatherTool.class);

    private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WeatherTool() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Tool(name = "get_weather", description = "查询指定城市的实时天气和未来 3 天预报。"
            + "支持全球任意城市，输入城市名称即可（如：北京、Tokyo、London）。"
            + "返回当前温度、体感温度、湿度、风速、天气状况及 3 天预报。")
    public String getWeather(
            @ToolParam(description = "城市名称，支持中文或英文，如：北京、Shanghai、Tokyo") String city) {

        log.info("getWeather: {}", city);

        try {
            // 1. 地理编码：城市名 -> 经纬度
            JsonNode geoResult = geocode(city);
            if (geoResult == null) {
                return "未找到城市: " + city + "，请检查城市名称是否正确";
            }

            double latitude = geoResult.get("latitude").asDouble();
            double longitude = geoResult.get("longitude").asDouble();
            String resolvedName = geoResult.path("name").asText(city);
            String country = geoResult.path("country").asText("");

            // 2. 查询天气
            return fetchWeather(latitude, longitude, resolvedName, country);

        } catch (Exception e) {
            log.error("天气查询失败: {}", e.getMessage(), e);
            return "天气查询失败: " + e.getMessage();
        }
    }

    private JsonNode geocode(String city) throws Exception {
        String encoded = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = GEOCODING_URL + "?name=" + encoded + "&count=1&language=zh";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode root = objectMapper.readTree(response.body());
        JsonNode results = root.path("results");

        if (results.isArray() && !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    private String fetchWeather(double latitude, double longitude, String name, String country) throws Exception {
        String url = FORECAST_URL
                + "?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current=temperature_2m,apparent_temperature,relative_humidity_2m,weather_code,wind_speed_10m"
                + "&daily=temperature_2m_max,temperature_2m_min,weather_code,precipitation_sum"
                + "&timezone=auto"
                + "&forecast_days=3";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode root = objectMapper.readTree(response.body());

        return formatWeather(root, name, country);
    }

    private String formatWeather(JsonNode root, String name, String country) {
        StringBuilder sb = new StringBuilder();
        sb.append("【").append(name);
        if (!country.isEmpty()) {
            sb.append(", ").append(country);
        }
        sb.append("】天气\n\n");

        // 当前天气
        JsonNode current = root.path("current");
        if (!current.isMissingNode()) {
            double temp = current.get("temperature_2m").asDouble();
            double feelsLike = current.get("apparent_temperature").asDouble();
            int humidity = current.get("relative_humidity_2m").asInt();
            double windSpeed = current.get("wind_speed_10m").asDouble();
            int weatherCode = current.get("weather_code").asInt();

            sb.append("当前天气: ").append(describeWeather(weatherCode)).append("\n");
            sb.append("温度: ").append(temp).append("°C\n");
            sb.append("体感温度: ").append(feelsLike).append("°C\n");
            sb.append("湿度: ").append(humidity).append("%\n");
            sb.append("风速: ").append(windSpeed).append(" km/h\n");
        }

        // 未来 3 天预报
        JsonNode daily = root.path("daily");
        if (!daily.isMissingNode()) {
            sb.append("\n未来 3 天预报:\n");
            JsonNode dates = daily.path("time");
            JsonNode maxTemps = daily.path("temperature_2m_max");
            JsonNode minTemps = daily.path("temperature_2m_min");
            JsonNode codes = daily.path("weather_code");
            JsonNode precip = daily.path("precipitation_sum");

            for (int i = 0; i < dates.size(); i++) {
                String date = dates.get(i).asText();
                double maxT = maxTemps.get(i).asDouble();
                double minT = minTemps.get(i).asDouble();
                int code = codes.get(i).asInt();
                double precipitation = precip.get(i).asDouble();

                sb.append(String.format("  %s: %s, %.0f°C ~ %.0f°C",
                        date, describeWeather(code), maxT, minT));
                if (precipitation > 0) {
                    sb.append(String.format(", 降水 %.1fmm", precipitation));
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * WMO Weather interpretation codes
     * @see <a href="https://open-meteo.com/en/docs">WMO Codes</a>
     */
    private String describeWeather(int code) {
        return switch (code) {
            case 0 -> "晴";
            case 1 -> "大部晴朗";
            case 2 -> "多云";
            case 3 -> "阴天";
            case 45, 48 -> "雾";
            case 51, 53, 55 -> "毛毛雨";
            case 56, 57 -> "冻毛毛雨";
            case 61 -> "小雨";
            case 63 -> "中雨";
            case 65 -> "大雨";
            case 66, 67 -> "冻雨";
            case 71 -> "小雪";
            case 73 -> "中雪";
            case 75 -> "大雪";
            case 77 -> "雪粒";
            case 80, 81, 82 -> "阵雨";
            case 85, 86 -> "阵雪";
            case 95 -> "雷暴";
            case 96, 99 -> "雷暴伴冰雹";
            default -> "未知(" + code + ")";
        };
    }
}
