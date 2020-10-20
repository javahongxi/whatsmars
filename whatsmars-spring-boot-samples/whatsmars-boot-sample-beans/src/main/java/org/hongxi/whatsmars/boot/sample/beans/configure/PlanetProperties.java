package org.hongxi.whatsmars.boot.sample.beans.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by shenhongxi on 2020/6/23.
 */
@ConfigurationProperties(prefix = "planet")
public class PlanetProperties {

    private List<String> names;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
