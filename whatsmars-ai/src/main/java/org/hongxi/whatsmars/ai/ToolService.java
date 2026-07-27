package org.hongxi.whatsmars.ai;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 工具服务 - 提供给 AI 调用的函数
 * <p>
 * 使用 @Tool 注解标记的方法可以被 AI 模型自动识别和调用
 * </p>
 *
 * @author hongxi
 */
@Service
public class ToolService {

    /**
     * 获取当前日期
     *
     * @return 当前日期字符串
     */
    @Tool("获取当前日期，格式为 yyyy-MM-dd")
    public String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间字符串
     */
    @Tool("获取当前时间，格式为 HH:mm:ss")
    public String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /**
     * 计算两个数的和
     *
     * @param a 第一个数
     * @param b 第二个数
     * @return 两数之和
     */
    @Tool("计算两个整数的和")
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * 计算两个数的乘积
     *
     * @param a 第一个数
     * @param b 第二个数
     * @return 两数之积
     */
    @Tool("计算两个整数的乘积")
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * 查询城市天气（模拟）
     *
     * @param city 城市名称
     * @return 天气描述
     */
    @Tool("查询指定城市的天气情况")
    public String getWeather(String city) {
        // 实际项目中应该调用真实的天气 API
        return city + " 今天天气晴朗，气温 25°C，微风";
    }
}
