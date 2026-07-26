package org.hongxi.whatsmars.ai.deepseek.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * 天气工具类
 * <p>
 * 使用 @Tool 注解定义 AI 可调用的天气查询函数。
 * AI 模型在回答天气相关问题时会自动调用这些工具。
 * </p>
 *
 * @author hongxi
 */
@Component
public class WeatherTools {

    /**
     * 获取指定城市的当前天气信息
     *
     * @param city 城市名称，例如：北京、上海、广州、深圳
     * @return 天气描述
     */
    @Tool(description = "获取指定城市的当前天气信息，包括温度、天气状况和空气质量")
    public String getWeather(@ToolParam(description = "城市名称，例如：北京、上海、广州、深圳") String city) {
        // 模拟天气数据（实际项目中可以调用真实天气 API，如和风天气、OpenWeatherMap）
        return switch (city) {
            case "北京" -> "晴天，温度 25°C，空气质量良好，AQI 65";
            case "上海" -> "多云，温度 28°C，湿度 65%，微风";
            case "广州" -> "小雨，温度 30°C，注意带伞，湿度 85%";
            case "深圳" -> "晴天，温度 29°C，适合出行，紫外线较强";
            case "杭州" -> "阴天，温度 26°C，空气湿度 70%";
            default -> "未知城市 \"" + city + "\"，无法提供天气信息。支持的城市：北京、上海、广州、深圳、杭州";
        };
    }

    /**
     * 获取未来几天的天气预报
     *
     * @param city 城市名称
     * @param days 预报天数（1-7）
     * @return 天气预报
     */
    @Tool(description = "获取指定城市未来几天的天气预报")
    public String getWeatherForecast(@ToolParam(description = "城市名称") String city,
                                     @ToolParam(description = "预报天数，1到7天") int days) {
        if (days < 1 || days > 7) {
            return "预报天数必须在 1-7 天之间";
        }
        // 模拟预报数据
        StringBuilder forecast = new StringBuilder();
        forecast.append(city).append(" 未来 ").append(days).append(" 天天气预报：\n");
        String[] conditions = {"晴", "多云", "阴", "小雨", "晴转多云"};
        int[] temps = {24, 26, 25, 23, 27};
        for (int i = 0; i < Math.min(days, 5); i++) {
            forecast.append(String.format("第%d天：%s，温度 %d°C\n", i + 1, conditions[i % conditions.length], temps[i % temps.length]));
        }
        return forecast.toString();
    }
}
