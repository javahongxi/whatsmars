/**
 * Created by shenhongxi on 2017/3/27.
 */

package com.itlong.motan.demo;

public class DemoMotanServiceImpl implements DemoMotanService {

    public String hello(String name) {
        System.out.println(name);
        return "Hello " + name + "!";
    }

}
