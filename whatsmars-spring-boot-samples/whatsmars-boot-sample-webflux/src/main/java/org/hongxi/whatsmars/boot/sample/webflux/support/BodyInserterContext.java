package org.hongxi.whatsmars.boot.sample.webflux.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

/**
 * Created by shenhongxi on 2021/5/3.
 */
public class BodyInserterContext implements BodyInserter.Context {

	private final ExchangeStrategies exchangeStrategies;

	public BodyInserterContext() {
		this.exchangeStrategies = ExchangeStrategies.withDefaults();
	}

	public BodyInserterContext(ExchangeStrategies exchangeStrategies) {
		this.exchangeStrategies = exchangeStrategies;
	}

	@Override
	public List<HttpMessageWriter<?>> messageWriters() {
		return exchangeStrategies.messageWriters();
	}

	@Override
	public Optional<ServerHttpRequest> serverRequest() {
		return Optional.empty();
	}

	@Override
	public Map<String, Object> hints() {
		return Collections.emptyMap();
	}

}