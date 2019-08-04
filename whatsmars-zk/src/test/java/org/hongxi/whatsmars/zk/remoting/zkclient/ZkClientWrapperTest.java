package org.hongxi.whatsmars.zk.remoting.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ZkClientWrapperTest {
    private TestingServer zkServer;
    private ZkClientWrapper zkClientWrapper;

    @Before
    public void setUp() throws Exception {
        int zkServerPort = 2181;
        zkServer = new TestingServer(zkServerPort, true);
        zkClientWrapper = new ZkClientWrapper("127.0.0.1:" + zkServerPort, 10000);
    }

    @After
    public void tearDown() throws Exception {
        zkServer.stop();
    }

    @Test
    public void testConnectedStatus() {
        boolean connected = zkClientWrapper.isConnected();
        assertThat(connected, is(false));
        zkClientWrapper.start();

        IZkChildListener listener = mock(IZkChildListener.class);
        zkClientWrapper.subscribeChildChanges("/path", listener);
        zkClientWrapper.unsubscribeChildChanges("/path", listener);
    }
}