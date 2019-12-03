package org.hongxi.whatsmars.spring.data.repository;

import org.hongxi.whatsmars.spring.data.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	Customer findByFirstName(String firstName);

	List<Customer> findByLastName(String lastName);

}