package org.hongxi.summer.exception;

import org.hongxi.summer.rpc.RpcContext;

/**
 * Created by shenhongxi on 2020/6/26.
 */
public abstract class SummerAbstractException extends RuntimeException {
    private static final long serialVersionUID = -6842400415484759967L;

    protected SummerErrorMsg summerErrorMsg = SummerErrorMsgConstants.FRAMEWORK_DEFAULT_ERROR;
    protected String errorMsg;

    public SummerAbstractException() {
        super();
    }

    public SummerAbstractException(SummerErrorMsg summerErrorMsg) {
        super();
        this.summerErrorMsg = summerErrorMsg;
    }

    public SummerAbstractException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public SummerAbstractException(String message, SummerErrorMsg summerErrorMsg) {
        super(message);
        this.summerErrorMsg = summerErrorMsg;
        this.errorMsg = message;
    }

    public SummerAbstractException(String message, Throwable cause) {
        super(message, cause);
        this.errorMsg = message;
    }

    public SummerAbstractException(String message, Throwable cause, SummerErrorMsg summerErrorMsg) {
        super(message, cause);
        this.summerErrorMsg = summerErrorMsg;
        this.errorMsg = message;
    }

    public SummerAbstractException(Throwable cause) {
        super(cause);
    }

    public SummerAbstractException(Throwable cause, SummerErrorMsg summerErrorMsg) {
        super(cause);
        this.summerErrorMsg = summerErrorMsg;
    }

    @Override
    public String getMessage() {
        String message = getOriginMessage();
        return String.format("error_message: %s, status: %d, error_code: %d, request_id: %s",
                message, getStatus(), getErrorCode(), RpcContext.getContext().getRequestId());
    }

    public String getOriginMessage() {
        if (summerErrorMsg == null) return super.getMessage();

        if (errorMsg != null && !errorMsg.equals("")) {
            return errorMsg;
        }
        return summerErrorMsg.getMessage();
    }

    public int getStatus() {
        return summerErrorMsg != null ? summerErrorMsg.getStatus() : 0;
    }

    public int getErrorCode() {
        return summerErrorMsg != null ? summerErrorMsg.getErrorCode() : 0;
    }

    public SummerErrorMsg getSummerErrorMsg() {
        return summerErrorMsg;
    }
}
