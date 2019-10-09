package org.hongxi.whatsmars.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by shenhongxi on 2018/11/20.
 */
@RestController
@RequestMapping("/es")
public class SimpleController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private RestClient restClient;

    @RequestMapping("/indexExists/{indexName}")
    public Boolean indexExists(@PathVariable String indexName) {
        return elasticsearchTemplate.indexExists(indexName);
    }

    @RequestMapping("/search/{indices}")
    public List<Customer> query(@PathVariable String indices) {
        QueryBuilder queryBuilder= QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("firstName", "Alice"));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(indices)
                .withQuery(queryBuilder)
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, Customer.class);
    }

    @RequestMapping("/save")
    public String testEsRepo() {
        saveCustomers();
        return "OK";
    }

    @RequestMapping("/fetchAll")
    public Iterable<Customer> fetchAll() {
        return this.repository.findAll();
    }

    @RequestMapping("/findByFirstName")
    public Customer findByFirstName() {
        return this.repository.findByFirstName("Alice");
    }

    @RequestMapping("/findByLastName")
    public List<Customer> findByLastName() {
        return this.repository.findByLastName("Smith");
    }

    @PostMapping("/{indices}/_search")
    public Object search(@PathVariable String indices,
                         @RequestBody(required = false) JSONObject query) {
        StringBuilder endpoint = new StringBuilder("/").append(indices).append("/_search");
        Request request = new Request("POST", endpoint.toString());
        request.addParameter("rest_total_hits_as_int", "true");
        request.addParameter("ignore_throttled", "true");
        try {
            if (query != null) {
                request.setJsonEntity(query.toString());
            }
            Response response = restClient.performRequest(request);
            return responseToJSONObject(response);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    private JSONObject responseToJSONObject(Response response) throws IOException {
        String body = EntityUtils.toString(response.getEntity());
        return JSON.parseObject(body);
    }

    private void saveCustomers() {
        this.repository.deleteAll();
        this.repository.save(new Customer("Alice", "Smith"));
        this.repository.save(new Customer("Bob", "Smith"));
    }

}
