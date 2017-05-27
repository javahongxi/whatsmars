package com.itlong.whatsmars.earth.support.web.service.impl;

import com.itlong.whatsmars.earth.support.web.job.base.Task;
import com.itlong.whatsmars.earth.support.web.service.StandardTaskService;
import com.itlong.whatsmars.earth.support.web.service.TaskBaseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shenhongxi on 2016/7/12.
 */
public abstract class AbstractStandardTaskService implements StandardTaskService {

    @Autowired
    private TaskBaseService taskBaseService;

    @Override
    public boolean finished(boolean processResult, Task task) {
        if(processResult){
            return taskBaseService.updateSuccess(task);
        } else {
            taskBaseService.unlock(task);
        }
        return true;
    }

    @Override
    public boolean lock(Task task) {
        return taskBaseService.lock(task);
    }
}
