package org.hongxi.whatsmars.boot.sample.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by shenhongxi on 2020/8/29.
 */
@Service
public class DemoService {

    @Value("${name}")
    private String name;

    public String getName() {
        return name;
    }
}
