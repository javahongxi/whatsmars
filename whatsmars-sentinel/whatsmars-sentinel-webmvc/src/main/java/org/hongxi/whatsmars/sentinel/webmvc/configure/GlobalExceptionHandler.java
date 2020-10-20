package org.hongxi.whatsmars.sentinel.webmvc.configure;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.hongxi.whatsmars.common.result.Result;
import org.hongxi.whatsmars.common.result.ResultHelper;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shenhongxi on 2020/9/15.
 */
@ControllerAdvice
@Order(0)
public class GlobalExceptionHandler {

    @ExceptionHandler(BlockException.class)
    @ResponseBody
    public Result<Void> handleBlockException(BlockException e) {
        System.out.println("Blocked by Sentinel: " + e.getRule());
        return ResultHelper.newErrorResult(-1, "Blocked by Sentinel");
    }
}
