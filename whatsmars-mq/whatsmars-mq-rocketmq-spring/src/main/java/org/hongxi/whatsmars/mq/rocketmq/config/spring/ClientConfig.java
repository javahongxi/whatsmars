package org.hongxi.whatsmars.mq.rocketmq.config.spring;

import org.apache.rocketmq.common.MixAll;

/**
 * Created by shenhongxi on 2018/10/21.
 */
public class ClientConfig {

    protected String namesrvAddr = System.getProperty(MixAll.NAMESRV_ADDR_PROPERTY, System.getenv(MixAll.NAMESRV_ADDR_ENV));

    protected String instanceName = System.getProperty("rocketmq.client.name", "DEFAULT");

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
