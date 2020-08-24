package org.hongxi.summer.rpc;

/**
 * Created by shenhongxi on 2020/7/30.
 */
public interface ResponseFuture extends Future, Response {

    void onSuccess(Response response);

    void onFailure(Response response);

    long getCreateTime();

    void setReturnType(Class<?> clazz);
}
