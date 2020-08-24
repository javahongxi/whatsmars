package org.hongxi.summer.transport.netty;

import org.hongxi.summer.common.util.RequestIdGenerator;
import org.hongxi.summer.exception.SummerServiceException;
import org.hongxi.summer.rpc.*;
import org.hongxi.summer.transport.Channel;
import org.hongxi.summer.transport.MessageHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by shenhongxi on 2020/8/22.
 */
public class NettyClientTest {

    private NettyServer nettyServer;
    private NettyClient nettyClient;
    private DefaultRequest request;
    private URL url;
    private String interfaceName = "org.hongxi.summer.protocol.example.HelloService";

    @Before
    public void setUp() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("requestTimeout", "500");

        url = new URL("netty", "localhost", 18080, interfaceName, parameters);

        request = new DefaultRequest();
        request.setRequestId(RequestIdGenerator.getRequestId());
        request.setInterfaceName(interfaceName);
        request.setMethodName("hello");
        request.setParametersDesc("void");

        nettyServer = new NettyServer(url, new MessageHandler() {
            @Override
            public Object handle(Channel channel, Object message) {
                Request request = (Request) message;
                DefaultResponse response = new DefaultResponse();
                response.setRequestId(request.getRequestId());
                response.setValue("method: " + request.getMethodName() + " requestId: " + request.getRequestId());

                return response;
            }
        });

        nettyServer.open();
    }

    @After
    public void tearDown() {
        if (nettyClient != null) {
            nettyClient.close();
        }
        nettyServer.close();
    }

    @Test
    public void testNormal() throws InterruptedException {
        nettyClient = new NettyClient(url);
        nettyClient.open();

        Response response;
        try {
            response = nettyClient.request(request);
            Object result = response.getValue();

            Assert.assertNotNull(result);
            Assert.assertEquals("method: " + request.getMethodName() + " requestId: " + request.getRequestId(), result);
        } catch (SummerServiceException e) {
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(false);
        }

    }

    @Test
    public void testClient() throws InterruptedException {
        nettyServer.close();

        NettyTestClient nettyClient = new NettyTestClient(url);
        this.nettyClient = nettyClient;
        nettyClient.open();

        for (Channel channel : nettyClient.getChannels()) {
            assertFalse(channel.isAvailable());
        }

        nettyServer.open();

        try {
            nettyClient.request(request);
        } catch (Exception e) {
        }

        Thread.sleep(3000);
        for (Channel channel : nettyClient.getChannels()) {
            assertTrue(channel.isAvailable());
        }
    }

    class NettyTestClient extends NettyClient {

        public NettyTestClient(URL url) {
            super(url);
        }

        public List<Channel> getChannels() {
            return super.channels;
        }

        public Channel getChannel0() {
            return super.getChannel();
        }
    }
}
