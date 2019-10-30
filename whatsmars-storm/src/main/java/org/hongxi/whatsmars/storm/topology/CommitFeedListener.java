package org.hongxi.whatsmars.storm.topology;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * This spout simulates reading commits from a live stream by doing two things:
 * <p/>
 * 1) Reading a file containing commit data into a list of strings (one string per commit).
 * 2) When nextTuple() is called, emit a tuple for each string in the list.
 */
public class CommitFeedListener extends BaseRichSpout {
    private SpoutOutputCollector outputCollector;
    private List<String> commits;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("commit"));
    }

    @Override
    public void open(Map map,
                     TopologyContext context,
                     SpoutOutputCollector outputCollector) {
        this.outputCollector = outputCollector;

        try {
            commits = IOUtils.readLines(ClassLoader.getSystemResourceAsStream("changelog.txt"),
                    Charset.defaultCharset().name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void nextTuple() {
        for (String commit : commits) {
            outputCollector.emit(new Values(commit));
        }
    }
}