package org.hongxi.whatsmars.boot.sample.bean;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by shenhongxi on 2020/6/10.
 */
public class DemoBean implements InitializingBean {

    public DemoBean() {
        System.out.println("new DemoBean");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }
}
