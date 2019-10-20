package org.hongxi.whatsmars.elasticsearch;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2018/11/19.
 */
public class TransportClientTest {

    private static final String INDEX = "whatsmars";

    private TransportClient client;

    @Before
    public void getClient() throws Exception{
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    @Test
    public void index() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("user", "javahongxi")
                .field("postDate", new Date())
                .field("message", "Elasticsearch Study")
                .endObject();

        IndexResponse response = client.prepareIndex(INDEX, INDEX, "1")
                .setSource(builder)
                .get();
        assert DocWriteResponse.Result.CREATED == response.getResult();
    }

    @Test
    public void index2() {
        Map<String, Object> json = new HashMap<>();
        json.put("user","hongxi");
        json.put("postDate","2013-01-30");
        json.put("message","Elastic Stack Study");

        IndexResponse response = client.prepareIndex(INDEX, INDEX, "2")
                .setSource(json)
                .get();
        assert DocWriteResponse.Result.CREATED == response.getResult();
    }

    @Test
    public void delete() {
        DeleteResponse response = client.prepareDelete(INDEX, INDEX, "1").get();
        assert DocWriteResponse.Result.DELETED == response.getResult();
    }

    @Test
    public void update() throws Exception {
        UpdateRequest request = new UpdateRequest(INDEX, INDEX, "1");
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("postDate", new Date())
                .field("message", "Elastic Stack Study")
                .endObject();
        request.doc(builder);
        UpdateResponse response = client.update(request).get();
        assert DocWriteResponse.Result.UPDATED == response.getResult();
    }

    @Test
    public void get() {
        GetResponse response = client.prepareGet(INDEX, INDEX, "1").get();
        System.out.println(response.getSource());
        System.out.println(response.getSourceAsMap());
        System.out.println(response.getSourceAsString());
        assert response.getSource() != null;
    }

    @Test
    public void search() {
        SearchResponse response = client.prepareSearch(INDEX)
                .setQuery(QueryBuilders.queryStringQuery("study"))
                .get();
        System.out.println(JSON.toJSONString(response));
    }

    @After
    public void close() {
        client.close();
    }
}
