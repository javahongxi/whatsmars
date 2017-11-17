package com.itlong.whatsmars.spring.initializing;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2017/11/16.
 */
@Component
public class InitBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("================afterPropertiesSet");
    }
}
