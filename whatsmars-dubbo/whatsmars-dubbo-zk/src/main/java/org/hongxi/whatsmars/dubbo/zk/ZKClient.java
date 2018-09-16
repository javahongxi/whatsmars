package org.hongxi.whatsmars.dubbo.zk;

import org.apache.zookeeper.ZooKeeperMain;

public class ZKClient {
    public static void main(String[] args) throws Exception {
        ZooKeeperMain.main(args);
        // 列出根节点下的所有子节点：ls /
        // 列出某节点下的所有子节点：ls /dubbo
    }
}
