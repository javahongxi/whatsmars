package org.hongxi.whatsmars.motan.consumer;

import org.hongxi.whatsmars.motan.api.MotanDemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by javahongxi on 2017/6/30.
 */
@Component
@Order(value = 2)
public class DemoCommandRunner implements CommandLineRunner {
    @Resource(name = "motanDemoService")
    private MotanDemoService motanDemoService;

    @Override
    public void run(String... strings) throws Exception {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            System.out.println(motanDemoService.hello("motan" + i));
            Thread.sleep(500);
        }
        System.out.println("motan demo is finish.");
        System.exit(0);
    }
}
