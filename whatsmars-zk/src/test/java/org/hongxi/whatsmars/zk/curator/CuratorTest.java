package org.hongxi.whatsmars.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

/**
 * Created by shenhongxi on 2018/10/27.
 */
public class CuratorTest {

    @Test
    public void testCurator() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryNTimes(10, 5000));
        client.start();
        System.out.println("zk client started!");

        String path1 = "/test";
        Stat stat = client.checkExists().forPath(path1);
        if (stat == null) {
            // client.create()...
            // client.setData()...
            client.inTransaction()
                    .create().forPath(path1, "hongxi.org".getBytes())
                    .and().setData().forPath(path1, "org.hongxi".getBytes())
                    .and().commit();
        }
        System.out.println(new String(client.getData().forPath(path1)));

        client.delete().deletingChildrenIfNeeded().forPath(path1);
        System.out.println(client.getChildren().forPath("/"));

        String path2 = "/dubbo/org.hongxi.service.OrderService/providers";
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path2, "A".getBytes());
        stat = client.checkExists().forPath("/dubbo/org.hongxi.service.OrderService");
        if (stat != null) System.out.println(stat.getCzxid());
        stat = client.checkExists().forPath("/dubbo/org.hongxi.service.OrderService/providers");
        if (stat != null) System.out.println(stat.getMzxid());

    }
}
