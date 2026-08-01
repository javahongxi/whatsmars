package org.hongxi.whatsmars.ai.structured;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 结构化输出控制器
 * <p>
 * 演示 LangChain4j 的 Structured Output 能力：
 * AiService 接口方法返回 POJO/record 类型，框架自动将 LLM 的 JSON 响应反序列化为 Java 对象。
 * </p>
 * <p>
 * 测试示例：
 * <ul>
 *   <li>POST /ai/structured/extract-user - 提取用户信息</li>
 *   <li>POST /ai/structured/sentiment    - 情感分析</li>
 *   <li>POST /ai/structured/todos        - 提取待办事项</li>
 *   <li>POST /ai/structured/sql          - 自然语言转 SQL</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
@RestController
@RequestMapping("/ai/structured")
public class StructuredOutputController {

    private static final Logger log = LoggerFactory.getLogger(StructuredOutputController.class);

    private final StructuredOutputAssistant assistant;

    public StructuredOutputController(StructuredOutputAssistant assistant) {
        this.assistant = assistant;
    }

    /**
     * 从自然语言中提取用户信息
     * <p>
     * 测试: "我叫张三，今年28岁，是一名Java架构师，喜欢跑步和读书，邮箱是zhangsan@example.com"
     * </p>
     *
     * @param text 包含用户信息的自然语言
     * @return 结构化的 UserInfo 对象（JSON）
     */
    @PostMapping("/extract-user")
    public StructuredOutputAssistant.UserInfo extractUserInfo(@RequestParam String text) {
        log.info("提取用户信息: {}", text);
        StructuredOutputAssistant.UserInfo result = assistant.extractUserInfo(text);
        log.info("提取结果: {}", result);
        return result;
    }

    /**
     * 情感分析
     * <p>
     * 测试: "这个产品太棒了，用起来非常顺手，强烈推荐！"
     * </p>
     *
     * @param text 待分析文本
     * @return 情感分析结果（JSON）
     */
    @PostMapping("/sentiment")
    public StructuredOutputAssistant.SentimentResult analyzeSentiment(@RequestParam String text) {
        log.info("情感分析: {}", text);
        StructuredOutputAssistant.SentimentResult result = assistant.analyzeSentiment(text);
        log.info("分析结果: {}", result);
        return result;
    }

    /**
     * 提取待办事项
     * <p>
     * 测试: "明天要交项目报告，下午3点和王总开会讨论Q3预算，别忘了给小李发周报，下周一前完成代码review"
     * </p>
     *
     * @param text 包含待办事项的文本
     * @return 待办事项列表（JSON）
     */
    @PostMapping("/todos")
    public StructuredOutputAssistant.TodoList extractTodos(@RequestParam String text) {
        log.info("提取待办事项: {}", text);
        StructuredOutputAssistant.TodoList result = assistant.extractTodos(text);
        log.info("提取结果: {}", result);
        return result;
    }

    /**
     * 自然语言转 SQL
     * <p>
     * 测试: "查询年龄大于25岁的用户，按注册时间倒序排列，取前10条"
     * </p>
     *
     * @param description 自然语言查询描述
     * @return SQL 生成结果（JSON）
     */
    @PostMapping("/sql")
    public StructuredOutputAssistant.SqlResult generateSql(@RequestParam String description) {
        log.info("生成 SQL: {}", description);
        StructuredOutputAssistant.SqlResult result = assistant.generateSql(description);
        log.info("SQL 结果: {}", result);
        return result;
    }
}
