package org.hongxi.whatsmars.storm.topology;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class EmailExtractor extends BaseBasicBolt {
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("email"));
    }

    @Override
    public void execute(Tuple tuple,
                        BasicOutputCollector outputCollector) {
        String commit = tuple.getStringByField("commit");
        String[] parts = commit.split(" ");
        outputCollector.emit(new Values(parts[1]));
    }
}