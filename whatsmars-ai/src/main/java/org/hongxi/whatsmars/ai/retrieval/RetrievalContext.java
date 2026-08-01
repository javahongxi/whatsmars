package org.hongxi.whatsmars.ai.retrieval;

import java.util.Collections;
import java.util.List;

/**
 * 检索来源上下文（线程级隔离）
 * <p>
 * 在 ReRankingContentAggregator 中捕获本次请求的来源信息，
 * 在 Controller 层读取后通过 SSE 发送给前端。
 * 使用 ThreadLocal 保证并发安全（每个 SSE 请求运行在独立线程）。
 * </p>
 *
 * @author hongxi
 */
public final class RetrievalContext {

    private static final ThreadLocal<List<SourceInfo>> SOURCES = new ThreadLocal<>();

    private RetrievalContext() {
    }

    public static void setSources(List<SourceInfo> sources) {
        SOURCES.set(sources != null ? List.copyOf(sources) : Collections.emptyList());
    }

    public static List<SourceInfo> getSources() {
        return SOURCES.get() != null ? SOURCES.get() : Collections.emptyList();
    }

    public static void clear() {
        SOURCES.remove();
    }
}
