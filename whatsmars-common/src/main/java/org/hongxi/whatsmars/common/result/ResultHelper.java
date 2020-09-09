package org.hongxi.whatsmars.common.result;

/**
 * Created by shenhongxi on 2018/5/18.
 */
public class ResultHelper {

    public static Result newSuccessResult() {
        return newResult(true);
    }

    public static <T> Result newSuccessResult(T data) {
        Result result = newSuccessResult();
        result.setData(data);
        return result;
    }

    public static Result newErrorResult() {
        return newResult(false);
    }

    public static Result newResult(boolean success) {
        return newResult(success, null);
    }

    public static Result newResult(boolean success, String message) {
        if (success) {
            return new Result(200, message == null ? "操作成功" : message);
        } else {
            return new Result(500, message == null ? "系统繁忙，请稍后再试" : message);
        }
    }

    public static Result newResult(int code, String message) {
        return new Result(code, message);
    }

}
