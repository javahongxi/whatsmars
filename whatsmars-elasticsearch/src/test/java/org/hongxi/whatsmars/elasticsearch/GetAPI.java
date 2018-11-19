package org.hongxi.whatsmars.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

/**
 * Created by shenhongxi on 2018/11/19.
 */
public class GetAPI {

    private TransportClient client;

    @Before
    public void getClient() throws Exception{
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    @Test
    public void get() {
        GetResponse response = client.prepareGet("whatsmars", "fendodate", "1").get();
        System.out.println(response);
    }
}
