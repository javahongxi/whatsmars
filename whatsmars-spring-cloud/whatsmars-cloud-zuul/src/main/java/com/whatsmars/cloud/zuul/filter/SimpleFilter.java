package com.whatsmars.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * Created by shenhongxi on 2017/12/27.
 */
public class SimpleFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        System.out.println("Simple filter fun ...");
        return null;
    }
}
