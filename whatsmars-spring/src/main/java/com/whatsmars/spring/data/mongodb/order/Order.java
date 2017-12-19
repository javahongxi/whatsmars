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

import com.whatsmars.spring.data.mongodb.core.AbstractDocument;
import com.whatsmars.spring.data.mongodb.core.Address;
import com.whatsmars.spring.data.mongodb.core.Customer;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Oliver Gierke
 */
@Document
public class Order extends AbstractDocument {

	@DBRef
	private Customer customer;
	private Address billingAddress;
	private Address shippingAddress;
	private Set<LineItem> lineItems = new HashSet<LineItem>();

	/**
	 * Creates a new {@link Order} for the given {@link Customer}.
	 * 
	 * @param customer must not be {@literal null}.
	 * @param shippingAddress must not be {@literal null}.
	 */
	public Order(Customer customer, Address shippingAddress) {
		this(customer, shippingAddress, null);
	}

	/**
	 * Creates a new {@link Order} for the given {@link Customer}, shipping and billing {@link Address}.
	 * 
	 * @param customer must not be {@literal null}.
	 * @param shippingAddress must not be {@literal null}.
	 * @param billingAddress can be {@literal null}.
	 */
	@PersistenceConstructor
	public Order(Customer customer, Address shippingAddress, Address billingAddress) {

		Assert.notNull(customer);
		Assert.notNull(shippingAddress);

		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
	}

	/**
	 * Adds the given {@link LineItem} to the {@link Order}.
	 * 
	 * @param lineItem
	 */
	public void add(LineItem lineItem) {
		this.lineItems.add(lineItem);
	}

	/**
	 * Returns the {@link Customer} who placed the {@link Order}.
	 * 
	 * @return
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Returns the billing {@link Address} for this order.
	 * 
	 * @return
	 */
	public Address getBillingAddress() {
		return billingAddress != null ? billingAddress : shippingAddress;
	}

	/**
	 * Returns the shipping {@link Address} for this order;
	 * 
	 * @return
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * Returns all {@link LineItem}s currently belonging to the {@link Order}.
	 * 
	 * @return
	 */
	public Set<LineItem> getLineItems() {
		return Collections.unmodifiableSet(lineItems);
	}

	/**
	 * Returns the total of the {@link Order}.
	 * 
	 * @return
	 */
	public BigDecimal getTotal() {

		BigDecimal total = BigDecimal.ZERO;

		for (LineItem item : lineItems) {
			total = total.add(item.getTotal());
		}

		return total;
	}
}
