package org.hongxi.whatsmars.boot.sample.beans.register;

import org.hongxi.whatsmars.boot.sample.beans.Planet;
import org.hongxi.whatsmars.boot.sample.beans.factory.PlanetFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.List;

/**
 * Created by shenhongxi on 2020/6/23.
 */
public class PlanetBeanDefinitionRegistryPostProcessor2 implements BeanDefinitionRegistryPostProcessor {

    private List<String> names;

    public PlanetBeanDefinitionRegistryPostProcessor2(List<String> names) {
        this.names = names;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        names.forEach(name -> {
            GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
            genericBeanDefinition.setBeanClass(PlanetFactoryBean.class);
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, name);
            genericBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
            registry.registerBeanDefinition("planet2_" + name, genericBeanDefinition);
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        names.forEach(name -> {
            Planet planet = beanFactory.getBean("planet2_" + name, Planet.class);
            System.out.println(planet.getName());
        });
    }
}
