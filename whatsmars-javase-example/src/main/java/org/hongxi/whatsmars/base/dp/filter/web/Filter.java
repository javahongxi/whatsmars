package org.hongxi.whatsmars.base.dp.filter.web;

public interface Filter {
	void doFilter(Request request, Response response, FilterChain fc);
}
