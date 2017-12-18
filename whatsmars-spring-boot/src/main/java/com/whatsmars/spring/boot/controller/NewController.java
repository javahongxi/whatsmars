package com.whatsmars.spring.boot.controller;

import com.whatsmars.spring.boot.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2017/11/16.
 */
@RestController
@RequestMapping("/new")
public class NewController {

    @RequestMapping("/t")
    public Map<String, Object> t() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("domain", "toutiao.im");
        return m;
    }

    @RequestMapping("/e")
    public String e(@RequestParam(required = false) String error) {
        if (error != null) throw BusinessException.build(error);
        return "OK";
    }
}
