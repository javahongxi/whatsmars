package org.hongxi.whatsmars.boot.sample.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.hongxi.whatsmars.boot.sample.mybatisplus.mapper")
public class QuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickstartApplication.class, args);
    }
}
