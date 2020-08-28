package org.hongxi.whatsmars.boot.sample.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Properties;

/**
 * Created by shenhongxi on 2020/7/17.
 */
@Data
@ConfigurationProperties(prefix = "test")
public class TestProperties {

    private Map<String, String> map;
    private Properties properties;

    private String name;
}
