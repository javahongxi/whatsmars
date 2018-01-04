package org.hongxi.whatsmars.spring.boot.exception;

import org.hongxi.whatsmars.spring.boot.common.pojo.ReturnItem;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Created by shenhongxi on 2017/11/16.
 */
public class AppException extends RuntimeException {

    private Code errorCode;
    private String errorMsg;

    private AppException() {
    }

    @Override
    public String getMessage() {
        if (super.getMessage() != null) return super.getMessage();
        return this.getErrorMsg();
    }

    public static AppException build(Code errorCode, String errorMsg) {
        return new AppException().setErrorCode(errorCode).setErrorMsg(errorMsg);
    }

    public static AppException build(String errorMsg) {
        return new AppException().setErrorCode(Code.ERROR).setErrorMsg(errorMsg);
    }

    public static AppException build(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError e : bindingResult.getAllErrors()) {
            sb.append(e.getDefaultMessage()).append(";");
        }
        return new AppException().setErrorCode(Code.ERROR).setErrorMsg(sb.toString());
    }

    public Code getErrorCode() {
        return errorCode;
    }

    public AppException setErrorCode(Code errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public AppException setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ReturnItem<String> toResultItem() {
        return new ReturnItem<String>(getErrorCode().value, getErrorMsg());
    }

    public enum Code {
        SUCCESS(200),
        ERROR(400);
        private int value;

        Code(int code) {
            this.value = code;
        }

        public int getValue() {
            return value;
        }
    }

}
