package org.hongxi.whatsmars.boot.sample.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.hongxi.whatsmars.common.result.Result;
import org.hongxi.whatsmars.common.result.ResultHelper;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<Void> handleLogicException(HttpServletRequest request, BusinessException e) {
        return ResultHelper.newErrorResult(e.getCode(), e.getMsg());
    }

    /**
     * 不需要处理的异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public Result<Void> justThrow(HttpServletRequest request, Exception e) throws Exception {
        throw e;
    }

    /**
     * 处理请求参数异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class})
    @ResponseBody
    public Result<Void> handleParamException(HttpServletRequest request, Exception e) throws Exception {
        if (e instanceof ConstraintViolationException) {
            return ResultHelper.newErrorResult(403, e.getMessage());
        }
        return ResultHelper.newErrorResult(403, "参数错误");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Result<Void> handleException(HttpServletRequest request, Throwable e) throws Throwable {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        log.error("exception handled, request:{}", request.getRequestURI(), e);
        return ResultHelper.newErrorResult(500, e.getMessage());
    }
}
