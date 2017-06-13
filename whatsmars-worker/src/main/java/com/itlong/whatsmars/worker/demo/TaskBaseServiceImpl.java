package com.itlong.whatsmars.worker.demo;

import com.itlong.whatsmars.worker.base.Task;
import com.itlong.whatsmars.worker.base.TaskBaseService;
import com.itlong.whatsmars.worker.base.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shenhongxi on 2016/7/12.
 */
@Service("taskBaseService")
public class TaskBaseServiceImpl implements TaskBaseService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public void add(Task task) {
        taskDao.insert(task);
    }

    @Override
    public boolean updateSuccess(Task task) {
        try {
            return taskDao.updateSuccess(task) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean lock(Task task) {
        try {
            return taskDao.lock(task) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean unlock(Task task) {
        try {
            return taskDao.unlock(task) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Task> findLocked(int splitSeconds, int retries) {
        return taskDao.findLocked(splitSeconds, retries);
    }

    @Override
    public List<Task> findByType(String type) {
        return taskDao.findByType(type);
    }
}
