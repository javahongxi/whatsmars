package org.hongxi.whatsmars.spring.initializing;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by shenhongxi on 2017/11/16.
 */
@Component
public class InitBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("================afterPropertiesSet");
    }

    @PostConstruct
    public void init() {
        System.out.println("================init"); // before afterPropertiesSet
    }

    @PreDestroy
    public void destroy() {
        System.out.println("================destroy");
    }
}
