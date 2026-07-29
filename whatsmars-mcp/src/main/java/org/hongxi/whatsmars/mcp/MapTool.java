package org.hongxi.whatsmars.mcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 地图工具（基于百度地图 URI API）
 * <p>
 * 免费对外开放，无需申请密钥（AK）。
 * 通过构造标准 URI 调起百度地图，支持位置展示、路线查询、POI 检索等。
 * 返回的链接可直接在浏览器中打开，也可分享给用户。
 * </p>
 *
 * @see <a href="https://lbsyun.baidu.com/index.php?title=uri/api/web">百度地图 URI API</a>
 */
@Component
public class MapTool {

    private static final Logger log = LoggerFactory.getLogger(MapTool.class);

    private static final String BASE_URL = "http://api.map.baidu.com";
    private static final String SRC = "webapp.whatsmars.mcp";

    @Tool(name = "show_location", description = "在百度地图上展示某个地址的位置，返回可直接打开的地图链接。"
            + "支持中英文地址，如：北京天安门、Shanghai Oriental Pearl Tower。")
    public String showLocation(
            @ToolParam(description = "要展示的地址，如：北京天安门、上海东方明珠") String address) {

        log.info("showLocation: {}", address);

        String encoded = encode(address);
        String url = BASE_URL + "/geocoder"
                + "?address=" + encoded
                + "&output=html"
                + "&src=" + SRC;

        return "地图链接: " + url + "\n（在浏览器中打开即可查看「" + address + "」的位置）";
    }

    @Tool(name = "plan_route", description = "规划两地之间的出行路线，返回百度地图导航链接。"
            + "支持驾车、步行、公交、骑行四种出行方式。")
    public String planRoute(
            @ToolParam(description = "出发地，如：北京站") String origin,
            @ToolParam(description = "目的地，如：故宫博物院") String destination,
            @ToolParam(description = "出行方式：driving（驾车）、walking（步行）、transit（公交）、riding（骑行）") String mode,
            @ToolParam(description = "所在城市，如：北京、上海") String region) {

        log.info("planRoute: {} -> {} ({})", origin, destination, mode);

        String travelMode = (mode != null && !mode.isEmpty()) ? mode.toLowerCase() : "driving";
        String originEncoded = encode(origin);
        String destEncoded = encode(destination);

        String url = BASE_URL + "/direction"
                + "?destination=" + destEncoded
                + "&origin=" + originEncoded
                + "&mode=" + travelMode
                + "&region=" + encode(region != null ? region : "")
                + "&output=html"
                + "&src=" + SRC;

        String modeName = switch (travelMode) {
            case "walking" -> "步行";
            case "transit" -> "公交";
            case "riding" -> "骑行";
            default -> "驾车";
        };

        return "【" + region + "】" + origin + " → " + destination + "（" + modeName + "）\n"
                + "地图链接: " + url
                + "\n（在浏览器中打开即可查看路线规划）";
    }

    @Tool(name = "search_place", description = "在指定城市搜索 POI 地点，返回百度地图搜索结果链接。"
            + "如：搜索「北京 火锅店」「上海 星巴克」。")
    public String searchPlace(
            @ToolParam(description = "搜索关键词，如：火锅店、星巴克咖啡、加油站") String query,
            @ToolParam(description = "搜索城市，如：北京、上海、广州") String region) {

        log.info("searchPlace: {} in {}", query, region);

        String url = BASE_URL + "/place/search"
                + "?query=" + encode(query)
                + "&region=" + encode(region != null ? region : "全国")
                + "&output=html"
                + "&src=" + SRC;

        return "在「" + region + "」搜索「" + query + "」的结果:\n"
                + "地图链接: " + url
                + "\n（在浏览器中打开即可查看搜索结果）";
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
