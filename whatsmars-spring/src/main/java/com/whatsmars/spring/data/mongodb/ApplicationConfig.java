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
package com.whatsmars.spring.data.mongodb;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Oliver Gierke
 */
@Configuration
@ComponentScan
@EnableMongoRepositories
class ApplicationConfig extends AbstractMongoConfiguration {

	@Autowired
	private List<Converter<?, ?>> converters;

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.mongodb.config.AbstractMongoConfiguration#getDatabaseName()
	 */
	@Override
	protected String getDatabaseName() {
		return "e-store";
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.mongodb.config.AbstractMongoConfiguration#mongo()
	 */
	@Override
	public Mongo mongo() throws Exception {

		Mongo mongo = new Mongo();
		mongo.setWriteConcern(WriteConcern.SAFE);

		return mongo;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.mongodb.config.AbstractMongoConfiguration#customConversions()
	 */
	@Override
	public CustomConversions customConversions() {
		return new CustomConversions(converters);
	}
}
