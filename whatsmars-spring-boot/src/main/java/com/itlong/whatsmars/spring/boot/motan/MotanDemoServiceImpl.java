/**
 * Created by shenhongxi on 2017/3/27.
 */

package com.itlong.whatsmars.spring.boot.motan;

public class MotanDemoServiceImpl implements MotanDemoService {

    public String hello(String name) {
        System.out.println(name);
        return "Hello " + name + "!";
    }

}
