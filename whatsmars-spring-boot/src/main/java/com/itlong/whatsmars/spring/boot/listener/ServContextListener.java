package com.itlong.whatsmars.spring.boot.listener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by shenhongxi on 2017/11/15.
 */
@Component
public class ServContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("================Hello, Server....");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
