package org.hongxi.whatsmars.fs.web.controller;

import org.hongxi.whatsmars.common.pojo.ResultCode;
import org.hongxi.whatsmars.common.util.ResultHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: qing
 * Date: 14-10-15
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String def() {
        return "Welcome FileSystem!";
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpServletResponse response) {
        return "Welcome FileSystem!";
    }

    @RequestMapping("/error")
    @ResponseBody
    public String error(HttpServletRequest request) {
        String exception = (String)request.getAttribute("exception");
        return ResultHelper.renderAsJson(ResultCode.SYSTEM_ERROR, exception);
    }
}
