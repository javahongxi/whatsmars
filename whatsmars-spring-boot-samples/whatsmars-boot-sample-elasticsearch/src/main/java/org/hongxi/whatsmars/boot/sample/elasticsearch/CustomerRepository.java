package org.hongxi.whatsmars.boot.sample.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

	Customer findByFirstName(String firstName);

	List<Customer> findByLastName(String lastName);

}