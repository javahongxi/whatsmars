package com.itlong.whatsmars.earth.support.web.job.base;

import java.util.List;

/**
 * Created by shenhongxi on 2016/7/12.
 */
public interface TaskDao {

    void insert(Task task);

    int updateSuccess(Task task);

    int updateFailed(Task task);

    int lock(Task task);

    int unlock(Task task);

    List<Task> findLocked(int splitSeconds, int retries);

    List<Task> findByType(String type);
}
