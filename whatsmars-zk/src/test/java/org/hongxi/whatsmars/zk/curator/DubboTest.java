package org.hongxi.whatsmars.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhongxi on 2018/10/28.
 */
public class DubboTest {

    private static CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryNTimes(10, 5000));

    @Before
    public void start() {
        client.start();
    }

    @Test
    public void testDubbo() {
        String path = "/dubbo";
        print(path);
        final String path2 = "/dubbo/org.hongxi.whatsmars.dubbo.demo.api.DemoService";
        List<String> nodes = print(path2);
        nodes.forEach(node -> {
            String path3 = getPath(path2, node);
            List<String> nodes2 = print(path3);
            if (nodes2.size() > 0) {
                String path4 = getPath(path3, nodes2.get(0));
                print(path4);
            }
        });
    }

    private static String getPath(String parentPath, String node) {
        return parentPath + "/" + node;
    }

    private static List<String> print(String path) {
        List<String> nodes = new ArrayList<>();
        try {
            System.out.println("###### " + path);
            System.out.println(new String(client.getData().forPath(path)));
            nodes = client.getChildren().forPath(path);
            System.out.println(nodes.size());
            nodes.forEach(data -> System.out.println(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }
}
