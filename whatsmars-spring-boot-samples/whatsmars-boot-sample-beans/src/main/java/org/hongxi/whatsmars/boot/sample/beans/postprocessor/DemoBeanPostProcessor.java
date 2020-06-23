package org.hongxi.whatsmars.boot.sample.beans.postprocessor;

import org.hongxi.whatsmars.boot.sample.beans.DemoBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by shenhongxi on 2020/6/10.
 */
public class DemoBeanPostProcessor implements BeanPostProcessor {

    public DemoBeanPostProcessor() {
        System.out.println("new DemoBeanPostProcessor");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == DemoBean.class) {
            System.out.println("postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == DemoBean.class) {
            System.out.println("postProcessAfterInitialization");
        }
        return bean;
    }
}
