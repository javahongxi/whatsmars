package org.hongxi.whatsmars.boot.sample.async.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2021/3/4.
 * 为了演示循环依赖的问题，这里让 beanB 先实例化
 */
@DependsOn("beanB")
@Component
public class BeanA {

    @Autowired
    private BeanB b;

    public void send() {
        b.send();
    }
}
