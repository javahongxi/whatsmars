package org.hongxi.whatsmars.boot.sample.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shenhongxi on 2018/11/20.
 */
@RestController
public class SimpleController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping("/indexExists/{indexName}")
    public Boolean indexExists(@PathVariable String indexName) {
        return elasticsearchTemplate.indexExists(indexName);
    }
}
