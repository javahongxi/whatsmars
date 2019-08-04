package org.hongxi.whatsmars.zk.remoting;

public interface ZookeeperTransporter {

    ZookeeperClient connect(String serverAddr, String authority);

}
