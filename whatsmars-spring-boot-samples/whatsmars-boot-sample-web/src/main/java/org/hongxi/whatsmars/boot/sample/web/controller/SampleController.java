package org.hongxi.whatsmars.boot.sample.web.controller;

import org.hongxi.whatsmars.common.result.Result;
import org.hongxi.whatsmars.common.result.ResultHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@RestController
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/hello")
    public Result<String> hello(@RequestParam String userId,
                                @RequestParam Integer age) {
        String hello = userId + "," + age;
        return ResultHelper.newSuccessResult(hello);
    }
}
