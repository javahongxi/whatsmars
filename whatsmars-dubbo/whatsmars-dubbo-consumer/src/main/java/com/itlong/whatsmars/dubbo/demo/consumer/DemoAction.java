/**
 * Created by shenhongxi on 2017/6/21.
 */
package com.itlong.whatsmars.dubbo.demo.consumer;

import com.itlong.whatsmars.dubbo.demo.DemoService;
import com.itlong.whatsmars.dubbo.demo.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoAction {
    
    private DemoService demoService;

    private UserService userService;

    public void setDemoService(DemoService demoService) {
        this.demoService = demoService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void start() throws Exception {
        for (int i = 0; i < Integer.MAX_VALUE; i ++) {
            try {
            	String hello = demoService.sayHello("world" + i);
            	String hi = userService.sayHi("java" + i);
                System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + hello);
                System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + hi);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(2000);
        }
	}

}