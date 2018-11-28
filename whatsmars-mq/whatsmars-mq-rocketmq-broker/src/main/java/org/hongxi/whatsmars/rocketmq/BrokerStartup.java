package org.hongxi.whatsmars.rocketmq;

/**
 * Created by shenhongxi on 2017/6/21.
 */
public class BrokerStartup {

    public static void main(String[] args) {
        String classpath = BrokerStartup.class.getResource("/").getPath();
        args = new String[] {"-c", classpath + "broker.properties"};
        org.apache.rocketmq.broker.BrokerStartup.main(args);
    }
}
