package org.hongxi.${artifactIdPackage}.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.hongxi.${artifactIdPackage}.api.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }
}
