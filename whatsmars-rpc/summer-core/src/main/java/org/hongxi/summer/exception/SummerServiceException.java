package org.hongxi.summer.exception;

/**
 * Created by shenhongxi on 2020/7/25.
 */
public class SummerServiceException extends SummerAbstractException {
    private static final long serialVersionUID = 167949946546769763L;

    public SummerServiceException() {
        super(SummerErrorMsgConstants.SERVICE_DEFAULT_ERROR);
    }

    public SummerServiceException(SummerErrorMsg summerErrorMsg) {
        super(summerErrorMsg);
    }

    public SummerServiceException(String message) {
        super(message, SummerErrorMsgConstants.SERVICE_DEFAULT_ERROR);
    }

    public SummerServiceException(String message, SummerErrorMsg summerErrorMsg) {
        super(message, summerErrorMsg);
    }

    public SummerServiceException(String message, Throwable cause) {
        super(message, cause, SummerErrorMsgConstants.SERVICE_DEFAULT_ERROR);
    }

    public SummerServiceException(String message, Throwable cause, SummerErrorMsg summerErrorMsg) {
        super(message, cause, summerErrorMsg);
    }

    public SummerServiceException(Throwable cause) {
        super(cause, SummerErrorMsgConstants.SERVICE_DEFAULT_ERROR);
    }

    public SummerServiceException(Throwable cause, SummerErrorMsg summerErrorMsg) {
        super(cause, summerErrorMsg);
    }
}
