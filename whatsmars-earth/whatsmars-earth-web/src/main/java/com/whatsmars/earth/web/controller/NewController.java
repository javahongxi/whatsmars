package com.whatsmars.earth.web.controller;

import com.whatsmars.earth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whatsmars.earth.domain.pojo.User;

import java.util.Date;

/**
 * Created by javahongxi on 2017/10/22.
 */
@RestController
@RequestMapping("/test")
public class NewController {

    @Autowired
    private UserService userService;

    // localhost:8080/test/add.jhtml
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public HttpStatus add(@RequestParam(name = "name") String username,
                          @RequestParam(required = false) String nickname,
                          @RequestParam(required = false, defaultValue = "1") Integer gender,
                          @RequestParam Integer age) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setGender(gender);
        user.setAge(age);
        userService.add(user);
        return HttpStatus.OK;
    }

}

