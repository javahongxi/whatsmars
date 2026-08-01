package org.hongxi.whatsmars.ai.retrieval;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.aggregator.ContentAggregator;
import dev.langchain4j.rag.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于 LLM 的重排序内容聚合器
 * <p>
 * 在多路检索结果合并后，利用 LLM 对候选内容按原始查询的相关性进行评分（0-10），
 * 按分数降序排列后返回 Top-N，同时捕获来源信息到 {@link RetrievalContext}。
 * </p>
 * <p>
 * 流程：
 * <ol>
 *   <li>收集所有查询的检索结果，按文本去重</li>
 *   <li>构造评分 Prompt，一次 LLM 调用完成全部打分</li>
 *   <li>解析评分结果，按分数降序排列</li>
 *   <li>取 Top-N 作为最终注入内容</li>
 *   <li>将来源信息（文件名、分数、重排分）存入 ThreadLocal</li>
 * </ol>
 * </p>
 *
 * @author hongxi
 */
public class ReRankingContentAggregator implements ContentAggregator {

    private static final Logger log = LoggerFactory.getLogger(ReRankingContentAggregator.class);

    private static final Pattern SCORE_PATTERN = Pattern.compile("(\\d+)\\.\\s*(\\d+)");

    private final ChatModel chatModel;
    private final int topN;

    public ReRankingContentAggregator(ChatModel chatModel, int topN) {
        this.chatModel = chatModel;
        this.topN = topN;
    }

    @Override
    public List<Content> aggregate(Map<Query, Collection<List<Content>>> queryToContents) {
        // 1. 收集所有候选内容，按文本去重
        List<Content> allCandidates = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (Collection<List<Content>> contentLists : queryToContents.values()) {
            for (List<Content> contents : contentLists) {
                for (Content c : contents) {
                    String text = c.textSegment().text();
                    if (seen.add(text)) {
                        allCandidates.add(c);
                    }
                }
            }
        }

        if (allCandidates.isEmpty()) {
            log.info("无候选内容，跳过重排序");
            RetrievalContext.setSources(List.of());
            return List.of();
        }

        // 候选数量 <= topN 时，无需重排
        if (allCandidates.size() <= topN) {
            log.info("候选数量({}) <= topN({})，跳过重排序", allCandidates.size(), topN);
            captureSources(allCandidates, new int[allCandidates.size()]);
            return allCandidates;
        }

        // 2. 构造评分 Prompt，一次 LLM 调用完成
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("请对以下候选资料与原始问题的相关性进行评分（0-10分）。\n");
            sb.append("原始问题: ").append(queryToContents.keySet().iterator().next().text()).append("\n\n");
            sb.append("候选资料:\n");
            for (int i = 0; i < allCandidates.size(); i++) {
                String snippet = allCandidates.get(i).textSegment().text();
                if (snippet.length() > 150) {
                    snippet = snippet.substring(0, 150) + "...";
                }
                sb.append(i + 1).append(". ").append(snippet).append("\n");
            }
            sb.append("\n请按以下格式输出评分（每行一条）:\n");
            sb.append("1. 分数\n2. 分数\n...");

            String response = chatModel.chat(sb.toString());
            int[] scores = parseScores(response, allCandidates.size());

            // 3. 按分数降序排列
            List<Content> ranked = rankByScore(allCandidates, scores);

            log.info("重排序完成，共 {} 条候选，取前 {} 条", allCandidates.size(), Math.min(topN, ranked.size()));
            for (int i = 0; i < Math.min(5, ranked.size()); i++) {
                log.debug("  #{} [score={}] {}", i + 1, scores[i],
                        ranked.get(i).textSegment().text().substring(0, Math.min(60, ranked.get(i).textSegment().text().length())));
            }

            // 4. 捕获来源信息
            captureSources(ranked, scores);

            // 5. 返回 Top-N
            return ranked.subList(0, Math.min(topN, ranked.size()));

        } catch (Exception e) {
            log.warn("LLM 重排序失败，使用原始顺序: {}", e.getMessage());
            int[] fallback = new int[allCandidates.size()];
            Arrays.fill(fallback, 5);
            captureSources(allCandidates, fallback);
            return allCandidates.subList(0, Math.min(topN, allCandidates.size()));
        }
    }

    /**
     * 解析 LLM 返回的评分文本
     */
    private int[] parseScores(String response, int expectedCount) {
        int[] scores = new int[expectedCount];
        Arrays.fill(scores, 5); // 默认中等分数

        if (response == null || response.isBlank()) {
            return scores;
        }

        Matcher matcher = SCORE_PATTERN.matcher(response);
        int idx = 0;
        while (matcher.find() && idx < expectedCount) {
            int score = Integer.parseInt(matcher.group(2));
            scores[idx++] = Math.max(0, Math.min(10, score));
        }

        if (idx == 0) {
            log.warn("未能解析到有效评分，使用默认分数");
        }
        return scores;
    }

    /**
     * 按评分降序排列内容
     */
    private List<Content> rankByScore(List<Content> candidates, int[] scores) {
        Integer[] indices = new Integer[candidates.size()];
        for (int i = 0; i < indices.length; i++) indices[i] = i;

        Arrays.sort(indices, (a, b) -> Integer.compare(scores[b], scores[a]));

        List<Content> ranked = new ArrayList<>();
        for (int idx : indices) {
            ranked.add(candidates.get(idx));
        }
        return ranked;
    }

    /**
     * 将来源信息存入 ThreadLocal
     */
    private void captureSources(List<Content> contents, int[] scores) {
        List<SourceInfo> sources = new ArrayList<>();
        for (int i = 0; i < contents.size(); i++) {
            TextSegment segment = contents.get(i).textSegment();
            String fileName = segment.metadata().getString("file_name");
            String scoreStr = segment.metadata().getString("score");
            double score = 0.0;
            if (scoreStr != null) {
                try {
                    score = Double.parseDouble(scoreStr);
                } catch (NumberFormatException ignored) {
                }
            }
            String text = segment.text();
            if (text.length() > 200) {
                text = text.substring(0, 200) + "...";
            }
            sources.add(new SourceInfo(text, fileName, score, i < scores.length ? scores[i] : 0));
        }
        RetrievalContext.setSources(sources);
    }
}
