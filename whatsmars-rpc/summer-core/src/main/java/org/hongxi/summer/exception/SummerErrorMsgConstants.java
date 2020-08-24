package org.hongxi.summer.exception;

/**
 * Created by shenhongxi on 2020/6/26.
 */
public class SummerErrorMsgConstants {

    // service error status 503
    public static final int SERVICE_DEFAULT_ERROR_CODE = 10001;
    public static final int SERVICE_REJECT_ERROR_CODE = 10002;
    public static final int SERVICE_TIMEOUT_ERROR_CODE = 10003;
    public static final int SERVICE_TASK_CANCEL_ERROR_CODE = 10004;
    // service error status 404
    public static final int SERVICE_NOT_FOUND_ERROR_CODE = 10101;
    // service error status 403
    public static final int SERVICE_REQUEST_LENGTH_OUT_OF_LIMIT_ERROR_CODE = 10201;

    // service error start

    public static final SummerErrorMsg SERVICE_DEFAULT_ERROR =
            new SummerErrorMsg(503, SERVICE_DEFAULT_ERROR_CODE, "service error");
    public static final SummerErrorMsg SERVICE_REJECT =
            new SummerErrorMsg(503, SERVICE_REJECT_ERROR_CODE, "service reject");
    public static final SummerErrorMsg SERVICE_NOT_FOUND =
            new SummerErrorMsg(404, SERVICE_NOT_FOUND_ERROR_CODE, "service not found");
    public static final SummerErrorMsg SERVICE_TIMEOUT =
            new SummerErrorMsg(503, SERVICE_TIMEOUT_ERROR_CODE, "service request timeout");

    // framework error
    public static final int FRAMEWORK_DEFAULT_ERROR_CODE = 20001;
    public static final int FRAMEWORK_ENCODE_ERROR_CODE = 20002;
    public static final int FRAMEWORK_DECODE_ERROR_CODE = 20003;
    public static final int FRAMEWORK_INIT_ERROR_CODE = 20004;
    public static final int FRAMEWORK_EXPORT_ERROR_CODE = 20005;
    // biz error
    public static final int BIZ_DEFAULT_ERROR_CODE = 30001;

    public static final SummerErrorMsg FRAMEWORK_DEFAULT_ERROR =
            new SummerErrorMsg(503, FRAMEWORK_DEFAULT_ERROR_CODE, "framework default error");

    public static final SummerErrorMsg FRAMEWORK_ENCODE_ERROR =
            new SummerErrorMsg(503, FRAMEWORK_ENCODE_ERROR_CODE, "framework encode error");
    public static final SummerErrorMsg FRAMEWORK_DECODE_ERROR =
            new SummerErrorMsg(503, FRAMEWORK_DECODE_ERROR_CODE, "framework decode error");
    public static final SummerErrorMsg FRAMEWORK_INIT_ERROR =
            new SummerErrorMsg(500, FRAMEWORK_INIT_ERROR_CODE, "framework init error");
    public static final SummerErrorMsg FRAMEWORK_EXPORT_ERROR =
            new SummerErrorMsg(503, FRAMEWORK_EXPORT_ERROR_CODE, "framework export error");

    public static final SummerErrorMsg BIZ_DEFAULT_EXCEPTION =
            new SummerErrorMsg(503, BIZ_DEFAULT_ERROR_CODE, "provider error");
}
