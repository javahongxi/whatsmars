package com.whatsmars.spring.boot.exception;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.whatsmars.spring.boot.common.ProfileUtils;
import com.whatsmars.spring.boot.common.pojo.ReturnItem;
import com.whatsmars.spring.boot.common.pojo.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 异常信息拦截
 * Created by shenhongxi on 2017/11/16.
 */
@ControllerAdvice(annotations = { RestController.class, Controller.class})
public class AppExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 业务异常处理，直接返回异常信息提示
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ReturnItem> businessExceptionHandle(AppException exception, HttpServletRequest request) {
        logError(exception, request, LogLevel.WARN);
        return new ResponseEntity<ReturnItem>(exception.toResultItem(), HttpStatus.OK);
    }

    /**
     * 其他为定义异常，统一返回默认错误信息，避免打印出异常堆栈
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ReturnItem> defaultExceptionHandle(Exception exception, HttpServletRequest request) {
        logError(exception, request, LogLevel.ERROR);
        return new ResponseEntity<ReturnItem>(new ReturnItem(AppException.Code.ERROR.getValue(), ProfileUtils.isDev() ? exception.getMessage() : ReturnMessage.Message.OPERATION_ERROR.getValue()), HttpStatus.OK);
    }


    public void logError(Exception ex, HttpServletRequest request, LogLevel logLevel) {
        Map<String, String> map = Maps.newHashMap();
        map.put("message", ex.getMessage());
        map.put("from", request.getRemoteAddr());
        final String queryString = request.getQueryString();
        map.put("path", queryString != null ? (request.getRequestURI() + "?" + queryString) : request.getRequestURI());

        switch (logLevel) {
            case ERROR:
                logger.error(JSON.toJSONString(map), ex);
                break;
            case WARN:
                logger.warn(JSON.toJSONString(map));
                break;
        }

    }

}
