package org.hongxi.whatsmars.zk.remoting.zkclient;

import org.hongxi.whatsmars.zk.remoting.ZookeeperClient;
import org.hongxi.whatsmars.zk.remoting.ZookeeperTransporter;

public class ZkclientZookeeperTransporter implements ZookeeperTransporter {

    @Override
    public ZookeeperClient connect(String serverAddr, String authority) {
        return new ZkclientZookeeperClient(serverAddr);
    }

}
