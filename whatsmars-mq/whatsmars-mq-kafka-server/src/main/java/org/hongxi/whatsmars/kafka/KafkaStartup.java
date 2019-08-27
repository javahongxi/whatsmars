package org.hongxi.whatsmars.kafka;

import kafka.Kafka;

/**
 * Created by shenhongxi on 2019-08-27.
 */
public class KafkaStartup {

    public static void main(String[] args) throws Exception {
        String classpath = KafkaStartup.class.getResource("/").getPath();
        args = new String[] {classpath + "server.properties"};
        Kafka.main(args);
    }
}
