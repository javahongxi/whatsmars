package com.itlong.whatsmars.earth.support.web.job.base;

import com.itlong.whatsmars.earth.support.web.service.SimpleTaskService;
import com.itlong.whatsmars.earth.support.web.service.StandardTaskService;
import com.itlong.whatsmars.earth.support.web.service.TaskBaseService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * Created by shenhongxi on 2016/7/11.
 */
public abstract class AbstractJobExecutor implements JobExecutor {

    protected TaskBaseService taskBaseService;

    protected int batchSize;

    protected String dbKey; // db1, db2

    protected String tableIndex; // _0000, _0001

    protected void execute(ThreadPoolTaskExecutor threadPoolTaskExecutor, StandardTaskService standardTaskService, List<Task> tasks) {
        JobRunnable jobRunnable = new JobRunnable();
        jobRunnable.setTasks(tasks);
        jobRunnable.setStandardTaskService(standardTaskService);
        jobRunnable.setTableIndex(tableIndex);
        jobRunnable.setDbKey(dbKey);
        threadPoolTaskExecutor.execute(jobRunnable);
    }

    protected void execute(ThreadPoolTaskExecutor threadPoolTaskExecutor, SimpleTaskService simpleTaskService, List<Task> tasks) {
        SimpleJobRunnable jobRunnable = new SimpleJobRunnable();
        jobRunnable.setTasks(tasks);
        jobRunnable.setSimpleTaskService(simpleTaskService);
        jobRunnable.setTableIndex(tableIndex);
        jobRunnable.setDbKey(dbKey);
        threadPoolTaskExecutor.execute(jobRunnable);
    }

    /**
     * 计算批处理执行次数
     * @param taskSize
     * @param batchSize
     * @return
     */
    protected int computeBatchTimes(int taskSize, int batchSize){
        int times = 0;
        if (taskSize % batchSize > 0) {
            times = taskSize / batchSize + 1;
        }else{
            times = taskSize / batchSize;
        }
        return times;
    }

    /**
     * 计算批处开始
     * @param taskSize
     * @param batchSize
     * @return
     */
    protected int computeBatchFromIndex(int taskSize, int batchSize, int i){
        int from = i * batchSize;
        if(from > taskSize){
            from = taskSize;
        }
        return from;
    }

    /**
     * 计算批处理结束
     * @param taskSize
     * @param batchSize
     * @return
     */
    protected int computeBatchToIndex(int taskSize, int batchSize, int i){
        int to = (i + 1) * batchSize;
        if (to > taskSize){
            to = taskSize;
        }
        return to;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
        DbContext.setDbKey(dbKey);
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
        DbContext.setTableIndex(tableIndex);
    }

    public TaskBaseService getTaskBaseService() {
        return taskBaseService;
    }

    public void setTaskBaseService(TaskBaseService taskBaseService) {
        this.taskBaseService = taskBaseService;
    }
}
