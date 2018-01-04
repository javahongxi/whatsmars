package org.hongxi.whatsmars.spring.factory;

import org.hongxi.whatsmars.spring.model.Mars;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Created by shenhongxi on 16/8/23.
 */
public class BeanFactoryTest {

    public static void main(String[] args) {
        testDefault();
    }

    private static void testXml() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource res = resolver.getResource("classpath:spring-context.xml");
        BeanFactory bf = new XmlBeanFactory(res);
        System.out.println("init BeanFactory");

        Mars mars = bf.getBean("mars", Mars.class);
        System.out.println(mars.getCnName() + ":" + mars.getAge());
    }

    private static void testDefault() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource res = resolver.getResource("classpath:spring-context.xml");
        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions(res);
        Mars mars = bf.getBean("mars", Mars.class);
        System.out.println(mars.getCnName() + ":" + mars.getAge());
    }
}
