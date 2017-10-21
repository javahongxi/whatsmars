package com.itlong.whatsmars.earth.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by javahongxi on 2017/10/22.
 */
@RestController
public class NewController {

    // ex. http://localhost:8080/t.jhtml
    @RequestMapping("/t")
    public Map<String, String> t() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "Lily");
        return map;
    }
}
