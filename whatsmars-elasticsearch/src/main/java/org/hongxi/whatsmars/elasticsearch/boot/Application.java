package org.hongxi.whatsmars.elasticsearch.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * Created by shenhongxi on 2018/11/19.
 */
@SpringBootApplication
public class Application {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        ElasticsearchTemplate template = context.getBean(ElasticsearchTemplate.class);
        System.out.println(template.indexExists("whatsmars"));
    }
}
