/**
 * Created by shenhongxi on 2017/3/27.
 */

package com.itlong.motan.demo;

import org.springframework.stereotype.Service;

@Service("demoMotanService")
public class DemoMotanServiceImpl implements DemoMotanService {

    public String hello(String name) {
        System.out.println(name);
        return "Hello " + name + "!";
    }

}
