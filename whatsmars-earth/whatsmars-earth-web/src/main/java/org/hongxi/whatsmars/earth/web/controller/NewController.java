package org.hongxi.whatsmars.earth.web.controller;

import org.apache.log4j.Logger;
import org.hongxi.whatsmars.common.result.Result;
import org.hongxi.whatsmars.common.result.ResultHelper;
import org.hongxi.whatsmars.earth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.hongxi.whatsmars.earth.domain.pojo.User;

/**
 * Created by javahongxi on 2017/10/22.
 */
@RestController
@RequestMapping("/test")
public class NewController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private UserService userService;

    // localhost:8080/test/add.jhtml
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestParam(name = "name") String username,
                      @RequestParam(required = false) String nickname,
                      @RequestParam(required = false, defaultValue = "1") Integer gender,
                      @RequestParam Integer age) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setNickname(nickname);
            user.setGender(gender);
            user.setAge(age);
            userService.add(user);
        } catch (Exception e) {
            logger.error("##########test add error", e);
            return ResultHelper.newErrorResult();
        }

        return ResultHelper.newSuccessResult();
    }

}

