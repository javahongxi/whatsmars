package org.hongxi.whatsmars.boot.sample.test;

import org.hongxi.whatsmars.boot.sample.test.Application;
import org.hongxi.whatsmars.boot.sample.test.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by shenhongxi on 2020/8/29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("dev")
public class ApplicationTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void demoService() {
        assert "a".equals(demoService.getName());
    }
}
