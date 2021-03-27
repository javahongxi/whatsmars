package org.hongxi.whatsmars.boot.sample.web.exception;

import lombok.Data;

/**
 * Created by shenhongxi on 2020/8/16.
 */
@Data
public class BusinessException extends RuntimeException {

    private int code;
    private String msg;

    public BusinessException(int code, String msg) {
        super("code:" + code + ", msg:" + msg);
        this.code = code;
        this.msg = msg;
    }
}
