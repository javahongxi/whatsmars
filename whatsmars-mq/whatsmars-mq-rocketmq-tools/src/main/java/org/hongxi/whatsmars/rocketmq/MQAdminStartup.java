package org.hongxi.whatsmars.rocketmq;

import org.apache.rocketmq.common.MixAll;

import java.util.ResourceBundle;

/**
 * Created by shenhongxi on 2018/10/21.
 */
public class MQAdminStartup {

    public static void main(String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("rocketmq");
        System.setProperty(MixAll.ROCKETMQ_HOME_PROPERTY, resourceBundle.getString("rocketmqHome"));
        org.apache.rocketmq.tools.command.MQAdminStartup.main(args);
        args = new String[] {"help", "brokerStatus"};
        org.apache.rocketmq.tools.command.MQAdminStartup.main(args);
    }
}
