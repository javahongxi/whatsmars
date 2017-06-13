package com.itlong.whatsmars.worker.base;

import java.util.List;

/**
 * Created by shenhongxi on 2016/7/12.
 */
public interface TaskBaseService {

    void add(Task task);

    boolean updateSuccess(Task task);

    boolean lock(Task task);

    boolean unlock(Task task);

    List<Task> findLocked(int splitSeconds, int retries);

    List<Task> findByType(String type);
}
