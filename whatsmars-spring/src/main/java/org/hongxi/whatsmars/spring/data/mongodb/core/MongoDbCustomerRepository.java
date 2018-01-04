/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hongxi.whatsmars.spring.data.mongodb.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * {@link CustomerRepository} implementation using the Spring Data {@link MongoOperations} API.
 * 
 * @author Oliver Gierke
 */
@Repository
@Profile("mongodb")
class MongoDbCustomerRepository implements CustomerRepository {

	private final MongoOperations operations;

	/**
	 * Creates a new {@link MongoDbCustomerRepository} using the given {@link MongoOperations}.
	 * 
	 * @param operations must not be {@literal null}.
	 */
	@Autowired
	public MongoDbCustomerRepository(MongoOperations operations) {

		Assert.notNull(operations);
		this.operations = operations;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.oreilly.springdata.mongodb.core.CustomerRepository#findOne(java.lang.Long)
	 */
	@Override
	public Customer findOne(Long id) {

		Query query = query(where("id").is(id));
		return operations.findOne(query, Customer.class);
	}

	/* 
	 * (non-Javadoc)
	 * @see com.oreilly.springdata.mongodb.core.CustomerRepository#save(com.oreilly.springdata.mongodb.core.Customer)
	 */
	@Override
	public Customer save(Customer customer) {

		operations.save(customer);
		return customer;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.oreilly.springdata.mongodb.core.CustomerRepository#findByEmailAddress(com.oreilly.springdata.mongodb.core.EmailAddress)
	 */
	@Override
	public Customer findByEmailAddress(EmailAddress emailAddress) {

		Query query = query(where("emailAddress").is(emailAddress));
		return operations.findOne(query, Customer.class);
	}
}
