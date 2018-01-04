package org.hongxi.whatsmars.earth.web.controller;

import org.hongxi.whatsmars.earth.domain.misc.LoginContextHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by javahongxi on 2017/12/24.
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "url", required = false )String url, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        if(LoginContextHolder.getLoginUser() != null) {
            mav.setViewName("redirect:/lender/index.jhtml");
            return mav;
        }
        mav.addObject("url", StringUtils.isNotBlank(url) ? url : null);
//        mav.addObject("token", this.createToken(request, response));
        return mav;
    }

    @RequestMapping("/example/list")
    public ModelAndView test() {
        ModelAndView mav = new ModelAndView();
        return mav;
    }
}
