package org.hongxi.whatsmars.ai.tc;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.hongxi.whatsmars.ai.tool.HttpRequestTool;
import org.hongxi.whatsmars.ai.tool.SystemInfoTool;
import org.hongxi.whatsmars.ai.tool.TimeTool;
import org.hongxi.whatsmars.ai.tool.WebSearchTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * Tool Calling 配置
 * <p>
 * 配置 LLM 工具调用的相关策略，包括工具名称幻觉处理。
 * 当 LLM 使用错误的工具名称（如方法名而非注册名）时，通过策略进行容错处理。
 * </p>
 *
 * @author hongxi
 */
@Configuration
public class ToolCallingConfig {

    /**
     * 工具名称幻觉策略
     * <p>
     * 当 LLM 尝试调用不存在的工具名称时（如调用 webSearch 而非 web_search），
     * 默认策略会抛出异常。此处配置为返回错误信息给 LLM，让其自行纠正。
     * </p>
     */
    @Bean
    public Function<ToolExecutionRequest, ToolExecutionResultMessage> hallucinatedToolNameStrategy() {
        return toolExecutionRequest -> ToolExecutionResultMessage.from(
                toolExecutionRequest,
                "错误：没有名为 " + toolExecutionRequest.name() + " 的工具，请检查可用工具列表并使用正确的工具名称。"
        );
    }

    /**
     * 创建 ToolCallingAssistant Bean
     * <p>
     * 使用 AiServices.builder() 编程式配置，支持 hallucinatedToolNameStrategy。
     * 注册所有工具：TimeTool、HttpRequestTool、WebSearchTool、SystemInfoTool。
     * </p>
     */
    @Bean
    public ToolCallingAssistant toolCallingAssistant(
            StreamingChatModel streamingChatModel,
            TimeTool timeTool,
            HttpRequestTool httpRequestTool,
            WebSearchTool webSearchTool,
            SystemInfoTool systemInfoTool,
            Function<ToolExecutionRequest, ToolExecutionResultMessage> hallucinatedToolNameStrategy) {
        return AiServices.builder(ToolCallingAssistant.class)
                .streamingChatModel(streamingChatModel)
                .tools(timeTool, httpRequestTool, webSearchTool, systemInfoTool)
                .hallucinatedToolNameStrategy(hallucinatedToolNameStrategy)
                .build();
    }
}
