package org.hongxi.whatsmars.sentinel.webmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
public class WebMvcTestController {

    @GetMapping("/hello")
    @ResponseBody
    public String apiHello() {
        doBusiness();
        return "Hello!";
    }

    @GetMapping("/err")
    @ResponseBody
    public String apiError() {
        doBusiness();
        return "Oops...";
    }

    @GetMapping("/foo/{id}")
    @ResponseBody
    public String apiFoo(@PathVariable("id") Long id) {
        doBusiness();
        return "Hello " + id;
    }

    @GetMapping("/exclude/{id}")
    @ResponseBody
    public String apiExclude(@PathVariable("id") Long id) {
        doBusiness();
        return "Exclude " + id;
    }
    
    @GetMapping("/forward")
    public ModelAndView apiForward() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("hello");
        return mav;
    }

    private void doBusiness() {
        Random random = new Random(1);
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
