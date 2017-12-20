package com.whatsmars.spring.boot.controller;

import com.whatsmars.spring.boot.exception.BusinessException;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/t", method = RequestMethod.GET)
    public Map<String, Object> query() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("domain", "toutiao.im");
        return m;
    }

    @RequestMapping(value = "/t", method = RequestMethod.POST)
    public Map<String, Object> add() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("domain", "toutiao.im");
        return m;
    }

    @RequestMapping(value = "/t", method = RequestMethod.PUT)
    public Map<String, Object> update() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("domain", "toutiao.im");
        return m;
    }

    @RequestMapping(value = "/t", method = RequestMethod.DELETE)
    public Map<String, Object> delete() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("domain", "toutiao.im");
        return m;
    }

    @RequestMapping(value = "/e", method = RequestMethod.GET)
    public HttpStatus e(@RequestParam(required = false) String error) {
        if (error != null) throw BusinessException.build(error);
        return HttpStatus.OK;
    }
}
