package org.hongxi.whatsmars.boot.sample.webflux.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2021/4/22.
 */
@Slf4j
public class DefaultExceptionHandler extends DefaultErrorWebExceptionHandler {

    public DefaultExceptionHandler(ErrorAttributes errorAttributes,
                                   WebProperties.Resources resources,
                                   ErrorProperties errorProperties,
                                   ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        HttpStatus status;
        int code;
        String msg;
        Throwable error = super.getError(request);
        if (error instanceof BusinessException) {
            status = HttpStatus.OK;
            code = ((BusinessException) error).getCode();
            msg = error.getMessage();
            options.excluding(ErrorAttributeOptions.Include.STACK_TRACE);
        } else if (error instanceof ResponseStatusException &&
                ((ResponseStatusException) error).getStatus().is4xxClientError()) {
            status = ((ResponseStatusException) error).getStatus();
            code = status.value();
            if (status == HttpStatus.BAD_REQUEST) {
                msg = "参数错误";
            } else {
                msg = error.getMessage();
            }
        } else {
            log.error("exception handled, request:{}", request.path(), error);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            code = status.value();
            msg = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }
        errorAttributes.put("status", status.value());
        errorAttributes.put("code", code);
        errorAttributes.put("msg", msg);
        return errorAttributes;
    }
}
