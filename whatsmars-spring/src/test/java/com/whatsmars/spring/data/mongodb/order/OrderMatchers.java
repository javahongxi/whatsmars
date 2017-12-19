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
package com.whatsmars.spring.data.mongodb.order;

import com.whatsmars.spring.data.mongodb.core.Product;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;

/**
 * Matchers to ease assertions on found {@link Order}s.
 * 
 * @author Oliver Gierke
 */
public class OrderMatchers {

	/**
	 * Matches if the source {@link Iterable} has an {@link Order} that matches the given {@link Matcher}.
	 * 
	 * @param matcher must not be {@literal null}.
	 * @return
	 */
	public static <T> Matcher<Iterable<? super T>> containsOrder(Matcher<? super T> matcher) {
		return hasItem(matcher);
	}

	/**
	 * Matches if the {@link Order} has a {@link LineItem} matching the given {@link Matcher}.
	 * 
	 * @param matcher must not be {@literal null}.
	 * @return
	 */
	public static Matcher<Order> LineItem(Matcher<LineItem> matcher) {
		return hasProperty("lineItems", hasItem(matcher));
	}

	/**
	 * Matches if the {@link LineItem} refers to a {@link Product} that matches the given {@link Matcher}.
	 * 
	 * @param matcher must not be {@literal null}.
	 * @return
	 */
	public static Matcher<LineItem> Product(Matcher<Product> matcher) {
		return hasProperty("product", matcher);
	}
}
