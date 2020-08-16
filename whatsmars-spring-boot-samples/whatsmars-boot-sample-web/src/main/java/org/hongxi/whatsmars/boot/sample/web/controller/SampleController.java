package org.hongxi.whatsmars.boot.sample.web.controller;

import org.hongxi.whatsmars.boot.sample.web.model.JsonResponse;
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
    public JsonResponse<String> hello(@RequestParam String userId,
                                      @RequestParam Integer age) {
        String hello = userId + "," + age;
        return JsonResponse.success(hello);
    }
}
