package org.hongxi.whatsmars.dubbo.demo.provider;

import org.hongxi.whatsmars.dubbo.autoconfigure.SingleDubboConfigBindingProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by shenhongxi on 2018/2/14.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigurationTest {

    @Autowired
    private SingleDubboConfigBindingProperties config;

    @Test
    public void application() {
        String applicationName = config.getApplication().getName();
        assert "demo-provider".equals(applicationName);
    }

    @Test
    public void protocol() {
        assert config.getProtocol().getPort() == 20882;
    }
}
