package org.hongxi.whatsmars.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.RetryNTimes;

/**
 * Created by shenhongxi on 2018/10/27.
 */
public class WatcherTest {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryNTimes(10, 5000));
        client.start();

        PathChildrenCache watcher = new PathChildrenCache(client, "/dubbo", true);
        watcher.getListenable().addListener((client1, event) -> {
            ChildData data = event.getData();
            if (data == null) {
                System.out.println("No data in event[" + event + "]");
            } else {
                System.out.println("Receive event: "
                        + "type=[" + event.getType() + "]"
                        + ", path=[" + data.getPath() + "]"
                        + ", data=[" + new String(data.getData()) + "]"
                        + ", stat=[" + data.getStat() + "]");
            }
        });
        watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        System.out.println("Register zk watcher successfully!");

        Thread.sleep(Integer.MAX_VALUE);
    }
}
