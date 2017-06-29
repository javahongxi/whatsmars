package com.itlong.motan.demo;

import com.weibo.motan.demo.service.MotanDemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by javahongxi on 2017/6/30.
 */
@Component
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
