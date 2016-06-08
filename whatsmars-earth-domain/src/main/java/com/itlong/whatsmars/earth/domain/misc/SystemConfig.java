package com.itlong.whatsmars.earth.domain.misc;

/**
 * Author: qing
 * Date: 14-10-30
 */
public class SystemConfig {

    private String cookieSecurityKey;//加密的key

    private String cookieDomain;//域

    private String cookieKey;//key

    private String hostName;

    private String imageHostName;

    private Double envRecharge;


    public String getCookieKey() {
        return cookieKey;
    }

    public void setCookieKey(String cookieKey) {
        this.cookieKey = cookieKey;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getCookieSecurityKey() {
        return cookieSecurityKey;
    }

    public void setCookieSecurityKey(String cookieSecurityKey) {
        this.cookieSecurityKey = cookieSecurityKey;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getImageHostName() {
        return imageHostName;
    }

    public void setImageHostName(String imageHostName) {
        this.imageHostName = imageHostName;
    }

    public Double getEnvRecharge() {
        return envRecharge;
    }

    public void setEnvRecharge(Double envRecharge) {
        this.envRecharge = envRecharge;
    }
}
