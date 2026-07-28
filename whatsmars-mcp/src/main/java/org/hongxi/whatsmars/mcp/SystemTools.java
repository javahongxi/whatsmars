package org.hongxi.whatsmars.mcp;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class SystemTools {

    @Tool(description = "将英文文本转换为大写形式")
    public String toUpperCase(@ToolParam(description = "要转换的英文文本") String text) {
        return text.toUpperCase();
    }

    @Tool(description = "将英文文本转换为小写形式")
    public String toLowerCase(@ToolParam(description = "要转换的英文文本") String text) {
        return text.toLowerCase();
    }

    @Tool(description = "反转字符串中的字符顺序")
    public String reverseString(@ToolParam(description = "要反转的字符串") String text) {
        return new StringBuilder(text).reverse().toString();
    }
}