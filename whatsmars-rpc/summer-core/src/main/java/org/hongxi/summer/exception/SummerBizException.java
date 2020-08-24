package org.hongxi.summer.exception;

/**
 * Created by shenhongxi on 2020/7/26.
 */
public class SummerBizException extends SummerAbstractException {
    private static final long serialVersionUID = -9030222846555573201L;

    public SummerBizException() {
        super(SummerErrorMsgConstants.BIZ_DEFAULT_EXCEPTION);
    }

    public SummerBizException(SummerErrorMsg summerErrorMsg) {
        super(summerErrorMsg);
    }

    public SummerBizException(String message) {
        super(message, SummerErrorMsgConstants.BIZ_DEFAULT_EXCEPTION);
    }

    public SummerBizException(String message, SummerErrorMsg summerErrorMsg) {
        super(message, summerErrorMsg);
    }

    public SummerBizException(String message, Throwable cause) {
        super(message, cause, SummerErrorMsgConstants.BIZ_DEFAULT_EXCEPTION);
    }

    public SummerBizException(String message, Throwable cause, SummerErrorMsg summerErrorMsg) {
        super(message, cause, summerErrorMsg);
    }

    public SummerBizException(Throwable cause) {
        super(cause, SummerErrorMsgConstants.BIZ_DEFAULT_EXCEPTION);
    }

    public SummerBizException(Throwable cause, SummerErrorMsg summerErrorMsg) {
        super(cause, summerErrorMsg);
    }
}
