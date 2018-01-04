package org.hongxi.whatsmars.shardingjdbc;

import org.hongxi.whatsmars.shardingjdbc.service.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    
    public static void main(final String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        applicationContext.getBean(DemoService.class).demo();
    }
}
