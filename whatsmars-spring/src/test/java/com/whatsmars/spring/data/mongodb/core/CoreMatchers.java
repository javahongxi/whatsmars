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

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

/**
 * Custom matchers to ease assertions on our domain classes.
 * 
 * @author Oliver Gierke
 */
public class CoreMatchers {

	/**
	 * Syntactic sugar to make Matchers more readable.
	 * 
	 * @param matcher must not be {@literal null}.
	 * @return
	 */
	public static <T> Matcher<T> with(Matcher<T> matcher) {
		return matcher;
	}

	/**
	 * Matches if the {@link Product} has the given name.
	 * 
	 * @param name must not be {@literal null}.
	 * @return
	 */
	public static Matcher<Product> named(String name) {
		return hasProperty("name", is(name));
	}
}
