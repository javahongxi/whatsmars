package com.itlong.whatsmars.worker.base;

/**
 * Created by shenhongxi on 2016/7/11.
 */
public interface StandardTaskService {

    boolean lock(Task task);

    boolean process(Task task);

    boolean finished(boolean processResult, Task task);
}
