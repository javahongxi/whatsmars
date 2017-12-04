package com.whatsmars.mq.rocketmq;

/**
 * Created by shenhongxi on 2017/6/21.
 */
public class BrokerStartup {

    public static void main(String[] args) {
        String classpath = BrokerStartup.class.getResource("/").getPath();
        args = new String[] {"-c", classpath + "broker.properties"};
        org.apache.rocketmq.broker.BrokerStartup.main(args);
        System.out.println("Broker started. name: " + org.apache.rocketmq.broker.BrokerStartup.properties.getProperty("brokerName"));
    }
}
