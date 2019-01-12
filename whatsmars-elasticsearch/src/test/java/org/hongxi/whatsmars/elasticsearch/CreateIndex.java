package org.hongxi.whatsmars.elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

/**
 * Created by shenhongxi on 2018/11/19.
 */
public class CreateIndex {

    private TransportClient client;

    @Before
    public void getClient() throws Exception{
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    /**
     * 使用ElasticSearch 帮助类
     * @throws IOException
     */
    @Test
    public void CreateXContentBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("user", "javahongxi")
                .field("postDate", new Date())
                .field("message", "Elasticsearch Stack Study")
                .endObject();

        IndexResponse response = client.prepareIndex("whatsmars", "test", "1").setSource(builder).get();
        System.out.println(response);
    }
}
