package org.hongxi.whatsmars.rocketmq;

import org.apache.rocketmq.common.MixAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by shenhongxi on 2018/10/21.
 */
@SpringBootApplication
public class MQAdminStartup {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MQAdminStartup.class, args);
        System.setProperty(MixAll.ROCKETMQ_HOME_PROPERTY, ctx.getEnvironment().getProperty("rocketmqHome"));
        org.apache.rocketmq.tools.command.MQAdminStartup.main(args);
        args = new String[] {"help", "brokerStatus"};
        org.apache.rocketmq.tools.command.MQAdminStartup.main(args);
    }
}
