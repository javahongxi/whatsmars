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
package com.whatsmars.spring.data.mongodb.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface to access {@link Product}s.
 * 
 * @author Oliver Gierke
 */
public interface ProductRepository extends CrudRepository<Product, Long>, QueryDslPredicateExecutor<Product> {

	/**
	 * Returns a {@link Page} of {@link Product}s having a description which contains the given snippet.
	 * 
	 * @param description
	 * @param pageable
	 * @return
	 */
	Page<Product> findByDescriptionContaining(String description, Pageable pageable);

	/**
	 * Returns all {@link Product}s having the given attribute.
	 * 
	 * @return
	 */
	@Query("{ ?0 : ?1 }")
	List<Product> findByAttributes(String key, String value);
}
