package org.hongxi.whatsmars.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by javahongxi on 2017/10/31.
 */
@RestController
@SpringBootApplication
@ImportResource({"classpath:esjob.xml"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/health")
    public String checkServerHealth(){
        return "OK";
    }
}
