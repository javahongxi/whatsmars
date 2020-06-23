package org.hongxi.whatsmars.boot.sample.beans.register;

import org.hongxi.whatsmars.boot.sample.beans.Planet;
import org.hongxi.whatsmars.boot.sample.beans.factory.PlanetFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.List;

/**
 * Created by shenhongxi on 2020/6/23.
 */
public class PlanetBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private List<String> names;

    public PlanetBeanDefinitionRegistryPostProcessor(List<String> names) {
        this.names = names;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        names.forEach(name -> {
            BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.rootBeanDefinition(PlanetFactory.class);
            beanBuilder.setFactoryMethod("createPlanet");
            beanBuilder.addConstructorArgValue(name);
            registry.registerBeanDefinition("planet_" + name, beanBuilder.getRawBeanDefinition());
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        names.forEach(name -> {
            Planet planet = beanFactory.getBean("planet_" + name, Planet.class);
            System.out.println(planet.getName());
        });
    }
}
