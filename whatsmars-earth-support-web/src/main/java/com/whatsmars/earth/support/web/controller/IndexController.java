package com.whatsmars.earth.support.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by shenhongxi on 15/4/13.
 */
@Controller
public class IndexController {

    @RequestMapping("/error")
    public ModelAndView error(@RequestParam(value = "messages",required = false)String messages) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("messages",messages);
        return mav;
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView();
    }

}
