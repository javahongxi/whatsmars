package org.hongxi.whatsmars.mq.rocketmq;

/**
 * Created by shenhongxi on 2017/6/21.
 */
public class BrokerStartup2 {

    public static void main(String[] args) {
        String classpath = BrokerStartup2.class.getResource("/").getPath();
        args = new String[] {"-c", classpath + "broker2.properties"};
        org.apache.rocketmq.broker.BrokerStartup.main(args);
    }
}
