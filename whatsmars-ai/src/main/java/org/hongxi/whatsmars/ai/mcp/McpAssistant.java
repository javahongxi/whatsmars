package org.hongxi.whatsmars.ai.mcp;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

/**
 * MCP 工具调用 AI 助手
 * <p>
 * 通过 MCP（Model Context Protocol）连接 whatsmars-mcp Server，
 * 使用其提供的天气查询和地图服务工具来回答用户问题。
 * </p>
 *
 * @author hongxi
 */
public interface McpAssistant {

    @SystemMessage("""
            你是一个智能助手，可以通过 MCP 协议调用 whatsmars-mcp Server 提供的工具来完成任务。
            当前可用的工具包括：
            - get_weather：查询指定城市的实时天气和未来 3 天预报
            - show_location：在百度地图上展示某个地址的位置
            - plan_route：规划两地之间的出行路线（驾车/步行/公交/骑行）
            - search_place：在指定城市搜索 POI 地点

            重要规则：
            1. 当用户询问天气时，必须调用 get_weather 工具获取实时数据，不要编造天气信息。
            2. 当用户询问位置、路线、搜索地点时，调用对应的地图工具。
            3. 不要编造结果，必须基于工具返回的实际数据回答。
            4. 用中文回答。
            """)
    TokenStream chat(String message);
}
