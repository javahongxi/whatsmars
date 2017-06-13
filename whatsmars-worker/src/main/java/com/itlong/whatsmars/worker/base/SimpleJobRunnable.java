package com.itlong.whatsmars.worker.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by shenhongxi on 2016/7/12.
 */
public class SimpleJobRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SimpleJobRunnable.class);

    private SimpleTaskService simpleTaskService;

    private List<Task> tasks;

    private String dbKey;

    private String tableIndex;

    @Override
    public void run() {
        if (tasks != null) {
            if (log.isDebugEnabled()) {
                log.debug("Current simple thread: " + Thread.currentThread().getName());
            }
            try {
                DbContext.setDbKey(dbKey);
                DbContext.setTableIndex(tableIndex);
                for (Task task : tasks) {
                    task.setTableIndex(tableIndex);
                    simpleTaskService.process(task);
                }
            } catch (Exception e) {
                log.error("Do simple task error", e);
                throw new RuntimeException("Do simple task error");
            }
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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

    public SimpleTaskService getSimpleTaskService() {
        return simpleTaskService;
    }

    public void setSimpleTaskService(SimpleTaskService simpleTaskService) {
        this.simpleTaskService = simpleTaskService;
    }
}
