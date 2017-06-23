package com.itlong.whatsmars.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by shenhongxi on 2017/3/27.
 */
@Controller
public class UserController {

    @Autowired
    private UserConfig userConfig;

    @RequestMapping("/")
    public String home(Map<String,Object> map) {
        map.put("hello", "Hi, boy!");
        return "index";
    }

    @RequestMapping("/do")
    @ResponseBody
    public String doSth() {
        return userConfig.getWelcome();
    }

}
