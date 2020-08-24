package org.hongxi.summer.exception;

/**
 * Created by shenhongxi on 2020/6/26.
 */
public class SummerFrameworkException extends SummerAbstractException {
    private static final long serialVersionUID = -6860263607854518306L;

    public SummerFrameworkException() {
        super(SummerErrorMsgConstants.FRAMEWORK_DEFAULT_ERROR);
    }

    public SummerFrameworkException(SummerErrorMsg summerErrorMsg) {
        super(summerErrorMsg);
    }

    public SummerFrameworkException(String message) {
        super(message, SummerErrorMsgConstants.FRAMEWORK_DEFAULT_ERROR);
    }

    public SummerFrameworkException(String message, SummerErrorMsg summerErrorMsg) {
        super(message, summerErrorMsg);
    }

    public SummerFrameworkException(String message, Throwable cause) {
        super(message, cause, SummerErrorMsgConstants.FRAMEWORK_DEFAULT_ERROR);
    }

    public SummerFrameworkException(String message, Throwable cause, SummerErrorMsg summerErrorMsg) {
        super(message, cause, summerErrorMsg);
    }

    public SummerFrameworkException(Throwable cause) {
        super(cause, SummerErrorMsgConstants.FRAMEWORK_DEFAULT_ERROR);
    }

    public SummerFrameworkException(Throwable cause, SummerErrorMsg summerErrorMsg) {
        super(cause, summerErrorMsg);
    }
}
