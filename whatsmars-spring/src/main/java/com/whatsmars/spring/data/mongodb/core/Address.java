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

import org.springframework.util.Assert;

/**
 * An address.
 * 
 * @author Oliver Gierke
 */
public class Address {

	private final String street, city, country;

	/**
	 * Creates a new {@link Address} from the given street, city and country.
	 * 
	 * @param street must not be {@literal null} or empty.
	 * @param city must not be {@literal null} or empty.
	 * @param country must not be {@literal null} or empty.
	 */
	public Address(String street, String city, String country) {

		Assert.hasText(street, "Street must not be null or empty!");
		Assert.hasText(city, "City must not be null or empty!");
		Assert.hasText(country, "Country must not be null or empty!");

		this.street = street;
		this.city = city;
		this.country = country;
	}

	/**
	 * Returns a copy of the current {@link Address} instance which is a new entity in terms of persistence.
	 * 
	 * @return
	 */
	public Address getCopy() {
		return new Address(this.street, this.city, this.country);
	}

	/**
	 * Returns the street.
	 * 
	 * @return
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Returns the city.
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the country.
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}
}
