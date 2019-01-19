package org.hongxi.whatsmars.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2018/11/19.
 */
public class ElasticsearchDemo {

    public static void main(String[] args) throws Exception {
        // on startup
        //此步骤添加IP，至少一个，如果设置了"client.transport.sniff"= true 一个就够了，因为添加了自动嗅探配置
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        Map<String, Object> json = new HashMap<>();
        json.put("user","hongxi");
        json.put("postDate","2013-01-30");
        json.put("message","Elasticsearch Study");

        IndexResponse response = client.prepareIndex("whatsmars", "hello", "1")
                .setSource(json)
                .get();
        System.out.println(response.getResult());

        // on shutdown
        client.close();
    }
}
