package org.hongxi.whatsmars.boot.sample.swagger.controller;

import io.swagger.annotations.Api;
import org.hongxi.whatsmars.boot.sample.swagger.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Api(tags = "Sample")
@RestController
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "Hello, " + name;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        System.out.println(user);
        return user;
    }
}
