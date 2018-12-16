package org.hongxi.whatsmars.spring.aspect.demo;

import org.hongxi.whatsmars.spring.aspect.Monitor;
import org.springframework.stereotype.Service;

/**
 * Created by shenhongxi on 2018/12/15.
 */
@Service
public class DemoService {

    @Monitor(tag = "spring.aspect.demo.DemoService.t")
    public void t() {
        for (int i = 0; i < 10000; i++) {
            if (i < 9999) System.out.print(i + ",");
            else System.out.println(i);
        }
    }
}
