package org.hongxi.whatsmars.spring.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:spring-mongo.xml")
public class MogoConfiguration {
}
