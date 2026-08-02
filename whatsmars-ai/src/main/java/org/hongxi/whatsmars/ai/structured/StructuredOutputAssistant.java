package org.hongxi.whatsmars.ai.structured;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

/**
 * 结构化输出 AI 助手接口
 * <p>
 * LangChain4j 的核心能力：AiService 接口方法可以返回 POJO/record 类型，
 * 框架会自动将 LLM 的 JSON 响应反序列化为对应的 Java 对象。
 * </p>
 * <p>
 * 演示三种结构化输出场景：
 * <ul>
 *   <li>信息提取 - 从自然语言中提取结构化数据（如用户信息、订单信息）</li>
 *   <li>分类判断 - 对文本进行分类或情感分析，返回枚举/标签</li>
 *   <li>列表提取 - 从文本中提取多条结构化记录</li>
 * </ul>
 * </p>
 *
 * @author hongxi
 */
public interface StructuredOutputAssistant {

    /**
     * 从自然语言中提取用户信息
     * <p>
     * 示例输入: "我叫张三，今年28岁，是一名Java架构师，喜欢跑步和读书，邮箱是zhangsan@example.com"
     * </p>
     *
     * @param message 包含用户信息的自然语言文本
     * @return 结构化的用户信息对象
     */
    @SystemMessage("""
                    你是一个信息提取助手，请从用户描述中提取个人信息。
                    如果某些信息无法确定，使用 null 或空列表表示。
                    你必须严格以 JSON 格式回复，包含字段: name(string), age(integer), email(string), occupation(string), hobbies(string数组)。
                    只返回 JSON，不要包含任何其他文字。
                    """)
    @UserMessage("请从以下描述中提取用户信息：{{message}}")
    UserInfo extractUserInfo(@V("message") String message);

    /**
     * 对文本进行情感分析
     *
     * @param message 待分析的文本
     * @return 情感分析结果
     */
    @SystemMessage("""
                    你是一个情感分析专家，请分析给定文本情感倾向。
                    你必须严格以 JSON 格式回复，包含字段: sentiment(枚举值 POSITIVE/NEGATIVE/NEUTRAL), confidence(0到1之间的浮点数), reason(字符串)。
                    只返回 JSON，不要包含任何其他文字。
                    """)
    @UserMessage("分析以下文本的情感：{{message}}")
    SentimentResult analyzeSentiment(@V("message") String message);

    /**
     * 从文本中提取待办事项
     * <p>
     * 示例输入: "明天要交项目报告，下午3点和王总开会讨论Q3预算，别忘了给小李发周报，下周一前完成代码review"
     * </p>
     *
     * @param message 包含待办事项的自然语言文本
     * @return 结构化的待办事项列表
     */
    @SystemMessage("""
                    你是一个待办事项提取助手。请从文本中提取所有待办事项，包括任务描述、优先级和截止日期。
                    优先级根据文本语境判断（HIGH/MEDIUM/LOW），截止日期使用 yyyy-MM-dd 格式，无法确定的使用 null。
                    你必须严格以 JSON 格式回复，格式为 {"items": [{"task": "...", "priority": "HIGH/MEDIUM/LOW", "dueDate": "yyyy-MM-dd"}]}。
                    只返回 JSON，不要包含任何其他文字。
                    """)
    @UserMessage("请提取以下文本中的待办事项：{{message}}")
    TodoList extractTodos(@V("message") String message);

    /**
     * 将自然语言转换为 SQL 语句
     *
     * @param message 数据查询的自然语言描述
     * @return 结构化的 SQL 信息
     */
    @SystemMessage("""
                    你是一个 SQL 专家，请根据用户的自然语言描述生成对应的 SQL 查询语句。
                    只返回 SQL 和简要说明，不要解释过程。
                    你必须严格以 JSON 格式回复，包含字段: sql(string), explanation(string), tablesUsed(string数组)。
                    只返回 JSON，不要包含任何其他文字。
                    """)
    @UserMessage("请为以下需求生成 SQL：{{message}}")
    SqlResult generateSql(@V("message") String message);

    // ========== DTO 定义 ==========

    /**
     * 用户信息
     */
    record UserInfo(
            String name,
            Integer age,
            String email,
            String occupation,
            List<String> hobbies
    ) {}

    /**
     * 情感分析结果
     */
    record SentimentResult(
            Sentiment sentiment,
            double confidence,
            String reason
    ) {}

    /**
     * 情感枚举
     */
    enum Sentiment {
        POSITIVE, NEGATIVE, NEUTRAL
    }

    /**
     * 待办事项列表
     */
    record TodoList(
            List<TodoItem> items
    ) {}

    /**
     * 单个待办事项
     */
    record TodoItem(
            String task,
            Priority priority,
            String dueDate
    ) {}

    /**
     * 优先级枚举
     */
    enum Priority {
        HIGH, MEDIUM, LOW
    }

    /**
     * SQL 生成结果
     */
    record SqlResult(
            String sql,
            String explanation,
            List<String> tablesUsed
    ) {}
}
