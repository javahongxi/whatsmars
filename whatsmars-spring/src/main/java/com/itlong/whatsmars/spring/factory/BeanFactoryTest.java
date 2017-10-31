package com.itlong.whatsmars.spring.factory;

import com.itlong.whatsmars.spring.model.Mars;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Created by shenhongxi on 16/8/23.
 */
public class BeanFactoryTest {

    public static void main(String[] args) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource res = resolver.getResource("classpath:spring-test.xml");
        BeanFactory bf = new XmlBeanFactory(res);
        System.out.println("init BeanFactory");

        Mars mars = bf.getBean("mars", Mars.class);
        System.out.println(mars.getCnName() + ":" + mars.getAge());
    }
}
