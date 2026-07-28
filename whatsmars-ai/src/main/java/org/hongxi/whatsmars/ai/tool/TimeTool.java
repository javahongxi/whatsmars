package org.hongxi.whatsmars.ai.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 时间工具
 * <p>
 * 提供当前时间查询和日期计算能力。LLM 的训练数据有截止日期，无法感知"现在"是什么时候，
 * 因此需要工具提供实时时间；日期差计算 LLM 经常算错，也需要工具保证准确性。
 * </p>
 *
 * @author hongxi
 */
@Component
public class TimeTool {

    private static final Map<DayOfWeek, String> WEEK_DAY_MAP = Map.of(
            DayOfWeek.MONDAY, "星期一",
            DayOfWeek.TUESDAY, "星期二",
            DayOfWeek.WEDNESDAY, "星期三",
            DayOfWeek.THURSDAY, "星期四",
            DayOfWeek.FRIDAY, "星期五",
            DayOfWeek.SATURDAY, "星期六",
            DayOfWeek.SUNDAY, "星期日"
    );

    /**
     * 获取当前日期和时间（精确到秒，含星期信息）
     */
    @Tool(name = "get_current_date_time", value = "获取当前的日期和时间，精确到秒，包含星期信息。格式：yyyy-MM-dd HH:mm:ss 星期X")
    public String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        String weekDay = WEEK_DAY_MAP.get(now.getDayOfWeek());
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + weekDay;
    }

    /**
     * 计算今天距离目标日期还有多少天
     */
    @Tool(name = "days_until", value = "计算今天距离目标日期还有多少天，用于倒计时、期限计算等场景")
    public String daysUntil(@P("目标日期，格式为 yyyy-MM-dd，例如：2026-12-31") String targetDate) {
        try {
            LocalDate target = LocalDate.parse(targetDate);
            long days = ChronoUnit.DAYS.between(LocalDate.now(), target);
            if (days > 0) {
                return "距离 " + targetDate + " 还有 " + days + " 天";
            } else if (days < 0) {
                return targetDate + " 已经过去 " + Math.abs(days) + " 天了";
            } else {
                return "今天就是 " + targetDate + "！";
            }
        } catch (Exception e) {
            return "日期格式错误，请使用 yyyy-MM-dd 格式";
        }
    }
}
