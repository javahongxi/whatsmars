package org.hongxi.whatsmars.boot.sample.webflux.exception;

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
public class DefaultExceptionHandler extends DefaultErrorWebExceptionHandler {

    public DefaultExceptionHandler(ErrorAttributes errorAttributes,
                                   WebProperties.Resources resources,
                                   ErrorProperties errorProperties,
                                   ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = super.getError(request);
        Map<String, Object> errorAttributes = new HashMap<>(4);
        int code;
        String msg;
        if (error instanceof BusinessException) {
            code = ((BusinessException) error).getCode();
            msg = error.getMessage();
            options.excluding(ErrorAttributeOptions.Include.STACK_TRACE);
        } else if (error instanceof ResponseStatusException &&
                ((ResponseStatusException) error).getStatus().is4xxClientError()) {
            code = ((ResponseStatusException) error).getStatus().value();
            msg = error.getMessage();
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            msg = "服务内部错误";
        }
        errorAttributes.put("code", code);
        errorAttributes.put("msg", msg);
        return errorAttributes;
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return HttpStatus.OK.value();
    }
}
