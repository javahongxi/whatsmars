package com.itlong.motan.demo;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
// 注意：依赖包里满足条件的配置也会加载进来，这里以demo开头确保只加载本module下的配置
@ImportResource(locations={"classpath*:spring/demo*.xml"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        // 在使用注册中心时要主动调用下面代码
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        System.out.println("server start...");
    }

}