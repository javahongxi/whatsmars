package org.hongxi.java.util.lang.singleton;

/**
 * @author shenhongxi 2019/8/11
 */
public enum EnumSingleton {

    INSTANCE;

    public String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
