package org.hongxi.whatsmars.boot.sample.beans.autoconfigure;

import org.hongxi.whatsmars.boot.sample.beans.Planet;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shenhongxi on 2020/6/23.
 */
@EnableConfigurationProperties(PlanetProperties.class)
@Configuration
public class PlanetRegisterConfiguration implements InitializingBean, ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    @Autowired
    private PlanetProperties planetProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        planetProperties.getNames().forEach(name -> {
            BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.rootBeanDefinition(Planet.class);
            beanBuilder.addPropertyValue("name", name);
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
            beanFactory.registerBeanDefinition("planet3_" + name, beanBuilder.getBeanDefinition());

            Planet planet = beanFactory.getBean("planet3_" + name, Planet.class);
            System.out.println(planet.getName());
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
