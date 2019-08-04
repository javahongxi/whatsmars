package org.hongxi.whatsmars.zk.remoting.curator;

import org.apache.curator.test.TestingServer;
import org.hongxi.whatsmars.zk.remoting.ZookeeperClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class CuratorZookeeperTransporterTest {
    private TestingServer zkServer;
    private ZookeeperClient zookeeperClient;

    @Before
    public void setUp() throws Exception {
        int zkServerPort = 2181;
        zkServer = new TestingServer(zkServerPort, true);
        zookeeperClient = new CuratorZookeeperTransporter().connect("127.0.0.1:2181", null);
    }

    @Test
    public void testZookeeperClient() {
        assertThat(zookeeperClient, not(nullValue()));
        zookeeperClient.close();
    }

    @After
    public void tearDown() throws Exception {
        zkServer.stop();
    }
}