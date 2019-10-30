package org.hongxi.whatsmars.storm.topology;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class EmailCounter extends BaseBasicBolt {
    private Map<String, Integer> counts;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // This bolt does not emit any values and therefore does not define any output fields.
    }

    @Override
    public void prepare(Map stormConf,
                        TopologyContext context) {
        counts = new HashMap<String, Integer>();
    }

    @Override
    public void execute(Tuple tuple,
                        BasicOutputCollector outputCollector) {
        String email = tuple.getStringByField("email");
        counts.put(email, countFor(email) + 1);
        printCounts();
    }

    private Integer countFor(String email) {
        Integer count = counts.get(email);
        return count == null ? 0 : count;
    }

    /**
     * Print the counts to System.out so you can easily see what's happening.
     */
    private void printCounts() {
        for (String email : counts.keySet()) {
            System.out.println(String.format("%s has count of %s", email, counts.get(email)));
        }
    }
}