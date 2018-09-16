package org.hongxi.whatsmars.javase.dp.filter.web;

public interface Filter {
	void doFilter(Request request, Response response, FilterChain fc);
}
