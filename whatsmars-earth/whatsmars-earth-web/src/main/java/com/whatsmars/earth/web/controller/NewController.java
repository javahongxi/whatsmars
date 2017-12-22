package com.whatsmars.earth.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whatsmars.earth.domain.pojo.User;

import java.util.Date;

/**
 * Created by javahongxi on 2017/10/22.
 */
@RestController
public class NewController {

    // ex. http://localhost:8080/t.jhtml?month=2017-11
    @RequestMapping("/t")
    public User t(@RequestParam(required = false) Date month) {
        User user = new User();
        user.setName("Lily");
        user.setCreated(month);
        return user;
    }
}

