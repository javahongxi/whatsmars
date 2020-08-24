package org.hongxi.summer.rpc;

import java.util.concurrent.Executor;

/**
 * Created by shenhongxi on 2020/8/22.
 */
public interface Callbackable {

    void addFinishCallback(Runnable runnable, Executor executor);

    void onFinish();
}
