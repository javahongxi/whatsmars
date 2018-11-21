package org.hongxi.whatsmars.boot.sample.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/indexExists/{indexName}")
    public Boolean indexExists(@PathVariable String indexName) {
        return elasticsearchTemplate.indexExists(indexName);
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

    private void saveCustomers() {
        this.repository.deleteAll();
        this.repository.save(new Customer("Alice", "Smith"));
        this.repository.save(new Customer("Bob", "Smith"));
    }

}
