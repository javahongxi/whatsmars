package org.hongxi.whatsmars.boot.sample.async.circular;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2021/3/4.
 *
 * Error creating bean with name 'beanB': Bean with name 'beanB' has been
 * injected into other beans [beanA] in its raw version as part of a circular
 * reference, but has eventually been wrapped.
 *
 * 用 @Lazy 解决循环依赖下使用 @Async 启动失败的问题
 */
@Slf4j
@Component
public class BeanB {

    @Lazy
    @Autowired
    private BeanA a;

    @Async
    public void send() {
        log.info("send ......");
    }
}
