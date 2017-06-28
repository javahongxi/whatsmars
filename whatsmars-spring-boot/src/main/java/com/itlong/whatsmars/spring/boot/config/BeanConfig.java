package com.itlong.whatsmars.spring.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by shenhongxi on 2017/6/21.
 */
@Configuration
@ImportResource(locations={"classpath:dubbo-demo-consumer.xml"})
public class BeanConfig {
}
