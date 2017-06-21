package com.itlong.whatsmars.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shenhongxi on 2017/3/27.
 */
@Controller
@EnableAutoConfiguration
public class UserController {

    @Autowired
    private UserConfig userConfig;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return userConfig.getNoFilterUrl().toString();
    }

}
