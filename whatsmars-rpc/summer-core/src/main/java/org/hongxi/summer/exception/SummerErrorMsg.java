package org.hongxi.summer.exception;

import java.io.Serializable;

/**
 * Created by shenhongxi on 2020/6/26.
 */
public class SummerErrorMsg implements Serializable {
    private static final long serialVersionUID = -5483348908144912517L;

    private int status;
    private int errorCode;
    private String message;

    public SummerErrorMsg(int status, int errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
