package com.itlong.whatsmars.worker.demo;

import com.itlong.whatsmars.worker.base.SimpleTaskService;
import com.itlong.whatsmars.worker.base.Task;

/**
 * Created by shenhongxi on 16/7/17.
 */
public class BizUnlockServiceImpl implements SimpleTaskService {
    @Override
    public boolean process(Task task) {
        return false;
    }
}
