package org.hongxi.whatsmars.zk.cs;

import org.junit.Test;

import java.util.Set;

/**
 * 假设一种场景：服务A向ZK注册自己的服务信息，比如IP + Port；客户端B向ZK获取服务的列表，并使用服务。
 * 比如CacheServer向zk注册ip和客户端port；其他client端可以向zk获取cacheserver的ip + port，以便此后建立链接。
 */
public class ConfigServerTest {

    @Test
    public void testConfigServer() {
        String serverType = "cache-server";
        try {
            ConfigManager manager = new ConfigManager(true);
            manager.add(serverType);
            ConfigServer s1 = new ConfigServer(serverType);
            ConfigServer s2 = new ConfigServer(serverType, true);
            ConfigServer s3 = new ConfigServer(serverType);
            ConfigClient c1 = new ConfigClient(serverType);
            ConfigClient c2 = new ConfigClient(serverType);
            ConfigClient c3 = new ConfigClient(serverType);
            Thread.sleep(3000);
            System.out.println("+++++++++++++++++++++++++");
            System.out.println("S1" + s1.getPath()); // 注意zk链接，是异步的，有可能此处为 null
            System.out.println("S2" + s2.getPath());
            System.out.println("S3" + s3.getPath());
            while (true) {
                System.out.println("-------------------------");
                Set<String> l1 = c1.getServers(serverType);
                if (l1 == null) {
                    System.out.println("l1 is null...");
                } else {
                    for (String path : l1) {
                        System.out.println("l1:" + path);
                    }
                }

                Set<String> l2 = c2.getServers(serverType);
                if (l2 == null) {
                    System.out.println("l2 is null...");
                } else {
                    for (String path : l2) {
                        System.out.println("l2:" + path);
                    }
                }

                Set<String> l3 = c3.getServers(serverType);
                if (l3 == null) {
                    System.out.println("l3 is null...");
                } else {
                    for (String path : l3) {
                        System.out.println("l3:" + path);
                    }
                }
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
