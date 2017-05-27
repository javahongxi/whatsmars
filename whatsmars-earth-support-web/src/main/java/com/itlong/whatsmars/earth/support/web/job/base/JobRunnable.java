package com.itlong.whatsmars.earth.support.web.job.base;

import com.itlong.whatsmars.earth.support.web.service.StandardTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by shenhongxi on 2016/7/12.
 */
public class JobRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(JobRunnable.class);

    private StandardTaskService standardTaskService;

    private List<Task> tasks;

    private String dbKey;

    private String tableIndex;

    @Override
    public void run() {
        if (tasks != null) {
            if (log.isDebugEnabled()) {
                log.debug("Current thread: " + Thread.currentThread().getName());
            }
            try {
                DbContext.setDbKey(dbKey);
                DbContext.setTableIndex(tableIndex);
                for (Task task : tasks) {
                    task.setTableIndex(tableIndex);

                    // 1. 一个job的多个实例，谁先成功锁定任务，谁先处理任务，若处理失败则解锁任务
                    // 2. 对于1中解锁失败的，要利用另外的job来专门进行解锁
                    // 3. 将任务分成几批，并行处理
                    // 4. 这些任务的子任务分批串行处理，同样有锁定-处理-失败解锁
                    // 5. 对于4中解锁失败的，同样要利用另外的job来专门进行解锁
                    boolean locked = standardTaskService.lock(task);
                    if (!locked) continue;

                    boolean result = standardTaskService.process(task);

                    standardTaskService.finished(result, task);
                }
            } catch (Exception e) {
                log.error("Do task error", e);
                throw new RuntimeException("Do task error");
            }
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public StandardTaskService getStandardTaskService() {
        return standardTaskService;
    }

    public void setStandardTaskService(StandardTaskService standardTaskService) {
        this.standardTaskService = standardTaskService;
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }
}
