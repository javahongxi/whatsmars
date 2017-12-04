package com.whatsmars.spring.boot.common;

import com.whatsmars.spring.boot.common.pojo.ReturnItem;
import com.whatsmars.spring.boot.common.pojo.ReturnItems;
import com.whatsmars.spring.boot.common.pojo.ReturnMessage;

import java.util.List;

/**
 * Created by shenhongxi on 2017/11/27.
 */
public abstract class ReturnItemUtils {

    public static ReturnItem newSuccessReturnItem() {
        return newReturnItem(true);
    }

    public static <T> ReturnItems newSuccessReturnItems(List<T> messages, Long total) {
        ReturnItems<T> items = new ReturnItems();
        items.setItems(messages);
        items.setTotal(total);
        items.setStatus(200);
        return items;
    }

    public static <T> ReturnItem newSuccessReturnItem(T message) {
        return newReturnItem(true, message);
    }

    public static ReturnItem newErrorReturnItem() {
        return newReturnItem(false);
    }

    public static ReturnItem newReturnItem(Boolean isSuccess) {
        return newReturnItem(isSuccess, null);
    }

    public static <T> ReturnItem<T> newReturnItem(Boolean isSuccess, T message) {
        if (Boolean.TRUE.equals(isSuccess)) {
            return new ReturnItem(200, returnItemMsg(message, ReturnMessage.Message.OPERATION_SUCCESS.getValue()));
        } else {
            return new ReturnItem(400, returnItemMsg(message, ReturnMessage.Message.OPERATION_SUCCESS.getValue()));
        }
    }

    private static Object returnItemMsg(Object message, String defaultMsg) {
        return null == message ? defaultMsg : message;
    }
}
