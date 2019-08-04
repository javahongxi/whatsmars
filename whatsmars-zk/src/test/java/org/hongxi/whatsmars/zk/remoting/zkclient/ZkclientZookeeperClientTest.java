package org.hongxi.whatsmars.zk.remoting.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.apache.curator.test.TestingServer;
import org.hongxi.whatsmars.zk.remoting.StateListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ZkclientZookeeperClientTest {
    private TestingServer zkServer;
    private ZkclientZookeeperClient zkclientZookeeperClient;

    @Before
    public void setUp() throws Exception {
        int zkServerPort = 281;
        zkServer = new TestingServer(zkServerPort, true);
        zkclientZookeeperClient = new ZkclientZookeeperClient("127.0.0.1:" + zkServerPort);
    }

    @Test
    public void testCheckExists() {
        String path = "/dubbo/org.apache.dubbo.demo.DemoService/providers";
        zkclientZookeeperClient.create(path, false);
        assertThat(zkclientZookeeperClient.checkExists(path), is(true));
        assertThat(zkclientZookeeperClient.checkExists(path + "/noneexits"), is(false));
    }

    @Test
    public void testDeletePath() {
        String path = "/dubbo/org.apache.dubbo.demo.DemoService/providers";
        zkclientZookeeperClient.create(path, false);
        assertThat(zkclientZookeeperClient.checkExists(path), is(true));

        zkclientZookeeperClient.delete(path);
        assertThat(zkclientZookeeperClient.checkExists(path), is(false));
    }

    @Test
    public void testConnectState() throws Exception {
        assertThat(zkclientZookeeperClient.isConnected(), is(true));
        final CountDownLatch stopLatch = new CountDownLatch(1);
        zkclientZookeeperClient.addStateListener(new StateListener() {
            @Override
            public void stateChanged(int connected) {
                stopLatch.countDown();
            }
        });
        zkServer.stop();
        stopLatch.await();
        assertThat(zkclientZookeeperClient.isConnected(), is(false));
    }

    @Test
    public void testChildrenListener() throws InterruptedException {
        String path = "/dubbo/org.apache.dubbo.demo.DemoService/providers";
        zkclientZookeeperClient.create(path, false);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        zkclientZookeeperClient.addTargetChildListener(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                countDownLatch.countDown();
            }
        });
        zkclientZookeeperClient.createPersistent(path + "/provider1");
        countDownLatch.await();
    }

    @Test
    public void testGetChildren() throws IOException {
        String path = "/dubbo/org.apache.dubbo.demo.DemoService/parentProviders";
        zkclientZookeeperClient.create(path, false);
        for (int i = 0; i < 5; i++) {
            zkclientZookeeperClient.createEphemeral(path + "/server" + i);
        }
        List<String> zookeeperClientChildren = zkclientZookeeperClient.getChildren(path);
        assertThat(zookeeperClientChildren, hasSize(5));
    }

    @After
    public void tearDown() throws Exception {
        zkclientZookeeperClient.close();
        zkServer.stop();
    }
}