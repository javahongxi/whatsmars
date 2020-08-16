package org.hongxi.whatsmars.boot.sample.web.exception;

import lombok.AllArgsConstructor;
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

    public BusinessException(ErrorCode errorCode) {
        super("code:" + errorCode.code + ", msg:" + errorCode.msg());
        this.code = errorCode.code();
        this.msg = errorCode.msg();
    }

    @AllArgsConstructor
    public enum ErrorCode {
        AUTH_FAIL(403, "请登录");

        private int code;
        private String msg;

        public int code() {
            return code;
        }

        public String msg() {
            return msg;
        }
    }
}
