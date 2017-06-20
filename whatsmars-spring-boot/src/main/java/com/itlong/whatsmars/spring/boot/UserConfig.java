package com.itlong.whatsmars.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by shenhongxi on 2017/6/20.
 */
@ConfigurationProperties(prefix="user")
public class UserConfig {
    private List<String> noFilterUrl;

    public List<String> getNoFilterUrl() {
        return noFilterUrl;
    }

    public void setNoFilterUrl(List<String> noFilterUrl) {
        this.noFilterUrl = noFilterUrl;
    }
}
