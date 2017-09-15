package com.itlong.whatsmars.motan.service;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by shenhongxi on 2017/6/30.
 */
@Component
@Order(value = 1)
public class MotanSwitcherRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // 在使用注册中心时要主动调用下面代码
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        System.out.println("provider started...");
    }
}
