package org.hongxi.whatsmars.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.hongxi.whatsmars.storm.topology.CommitFeedListener;
import org.hongxi.whatsmars.storm.topology.EmailCounter;
import org.hongxi.whatsmars.storm.topology.EmailExtractor;

public class LocalTopologyRunner {
    private static final int TEN_MINUTES = 600000;

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("commit-feed-listener", new CommitFeedListener());

        builder
                .setBolt("email-extractor", new EmailExtractor())
                .shuffleGrouping("commit-feed-listener");

        builder
                .setBolt("email-counter", new EmailCounter())
                .fieldsGrouping("email-extractor", new Fields("email"));

        Config config = new Config();
        config.setDebug(true);

        StormTopology topology = builder.createTopology();

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("github-commit-count-topology",
                config,
                topology);

        Utils.sleep(TEN_MINUTES);
        cluster.killTopology("github-commit-count-topology");
        cluster.shutdown();
    }
}
