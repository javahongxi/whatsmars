package com.whatsmars.spring.context.generic;

import com.whatsmars.spring.model.Mars;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by javahongxi on 2017/11/2.
 */
public class TestSpring {
    public static void main(String[] args) {
        GenericApplicationContext ctx = new GenericApplicationContext();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
        xmlReader.loadBeanDefinitions(new ClassPathResource("applicationContext.xml"));
        PropertiesBeanDefinitionReader propReader = new PropertiesBeanDefinitionReader(ctx);
        propReader.loadBeanDefinitions(new ClassPathResource("otherBeans.properties"));
        ctx.refresh();
        Mars mars = ctx.getBean(Mars.class);
        System.out.println(mars.getCnName());
    }
}
