package org.hongxi.whatsmars.pulsar;

import org.apache.pulsar.PulsarBrokerStarter;

/**
 * Created by shenhongxi on 2021/8/15.
 */
public class BrokerStartup {

    public static void main(String[] args) throws Exception {
        String classpath = BrokerStartup.class.getResource("/").getPath();
        args = new String[] {"-c", classpath + "broker.conf"};
        PulsarBrokerStarter.main(args);
    }
}
