package org.hongxi.whatsmars.zk.remoting.curator;

import org.hongxi.whatsmars.zk.remoting.ZookeeperClient;
import org.hongxi.whatsmars.zk.remoting.ZookeeperTransporter;

public class CuratorZookeeperTransporter implements ZookeeperTransporter {

    @Override
    public ZookeeperClient connect(String serverAddr, String authority) {
        return new CuratorZookeeperClient(serverAddr, authority);
    }

}
