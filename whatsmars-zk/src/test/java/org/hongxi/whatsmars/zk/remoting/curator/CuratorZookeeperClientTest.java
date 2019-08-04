package org.hongxi.whatsmars.zk.remoting.curator;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.WatchedEvent;
import org.hongxi.whatsmars.zk.remoting.ChildListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class CuratorZookeeperClientTest {
    private TestingServer zkServer;
    private CuratorZookeeperClient curatorClient;

    @Before
    public void setUp() throws Exception {
        int zkServerPort = 2181;
        zkServer = new TestingServer(zkServerPort, true);
        curatorClient = new CuratorZookeeperClient("127.0.0.1:" + zkServerPort, null);
    }

    @Test
    public void testCheckExists() {
        String path = "/dubbo/org.apache.dubbo.demo.DemoService/providers";
        curatorClient.create(path, false);
        assertThat(curatorClient.checkExists(path), is(true));
        assertThat(curatorClient.checkExists(path + "/noneexits"), is(false));
    }

    @Test
    public void testChildrenPath() {
        String path = "/dubbo/org.apache.dubbo.demo.DemoService/providers";
        curatorClient.create(path, false);
        curatorClient.create(path + "/provider1", false);
        curatorClient.create(path + "/provider2", false);

        List<String> children = curatorClient.getChildren(path);
        assertThat(children.size(), is(2));
    }

    @Test
    public void testChildrenListener() throws InterruptedException {
        String path = "/dubbo/org.apache.dubbo.demo.DemoService/providers";
        curatorClient.create(path, false);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        curatorClient.addTargetChildListener(path, new CuratorWatcher() {
            @Override
            public void process(WatchedEvent watchedEvent) throws Exception {
                countDownLatch.countDown();
            }
        });
        curatorClient.createPersistent(path + "/provider1");
        countDownLatch.await();
    }


    @Test(expected = IllegalStateException.class)
    public void testWithInvalidServer() {
        curatorClient = new CuratorZookeeperClient("127.0.0.1:2181", null);
        curatorClient.create("/testPath", true);
    }

    @Test(expected = IllegalStateException.class)
    public void testWithStoppedServer() throws IOException {
        curatorClient.create("/testPath", true);
        zkServer.stop();
        curatorClient.delete("/testPath");
    }

    @Test
    public void testRemoveChildrenListener() {
        ChildListener childListener = mock(ChildListener.class);
        curatorClient.addChildListener("/children", childListener);
        curatorClient.removeChildListener("/children", childListener);
    }

    @Test
    public void testCreateExistingPath() {
        curatorClient.create("/pathOne", false);
        curatorClient.create("/pathOne", false);
    }

    @Test
    public void testConnectedStatus() {
        curatorClient.createEphemeral("/testPath");
        boolean connected = curatorClient.isConnected();
        assertThat(connected, is(true));
    }

    @After
    public void tearDown() throws Exception {
        curatorClient.close();
        zkServer.stop();
    }
}