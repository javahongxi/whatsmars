package org.hongxi.whatsmars.boot.sample.beans;

import org.springframework.core.Ordered;

/**
 * Created by shenhongxi on 2020/6/21.
 */
public class OrderedDemoBean extends DemoBean implements Ordered {

    public OrderedDemoBean() {
        System.out.println("new OrderedDemoBean");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
