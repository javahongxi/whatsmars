package com.itlong.whatsmars.worker.demo;

import com.itlong.whatsmars.worker.base.StandardTaskService;
import com.itlong.whatsmars.worker.base.Task;
import com.itlong.whatsmars.worker.base.TaskBaseService;
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
