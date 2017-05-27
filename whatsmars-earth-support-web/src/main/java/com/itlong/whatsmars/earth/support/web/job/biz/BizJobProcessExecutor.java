package com.itlong.whatsmars.earth.support.web.job.biz;

import com.itlong.whatsmars.earth.support.web.job.base.AbstractJobExecutor;
import com.itlong.whatsmars.earth.support.web.job.base.DbContext;
import com.itlong.whatsmars.earth.support.web.job.base.Task;
import com.itlong.whatsmars.earth.support.web.service.StandardTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * Created by shenhongxi on 2016/7/14.
 */
public class BizJobProcessExecutor extends AbstractJobExecutor {

    private static final Logger log = LoggerFactory.getLogger(BizJobProcessExecutor.class);

    private ThreadPoolTaskExecutor commonThreadPool;

    private StandardTaskService standardTaskService;

    @Override
    public void execute() throws Exception {
        DbContext.setDbKey(dbKey);
        DbContext.setTableIndex(tableIndex);
        String taskType = new Integer(tableIndex).toString();
        try {
            List<Task> tasks = taskBaseService.findByType(taskType);
            if (tasks != null && tasks.size() > 0) {
                int taskSize = tasks.size();
                int batchTimes = computeBatchTimes(taskSize, batchSize);
                for (int i = 0; i < batchTimes; i++) {
                    int from = computeBatchFromIndex(taskSize, batchSize, i);
                    int to = computeBatchToIndex(taskSize, batchSize, i);
                    this.execute(commonThreadPool, standardTaskService, tasks.subList(from, to));
                }
            } else {
                log.info("没有未处理的任务");
            }
        } catch (Exception e) {

        }
    }

    public ThreadPoolTaskExecutor getCommonThreadPool() {
        return commonThreadPool;
    }

    public void setCommonThreadPool(ThreadPoolTaskExecutor commonThreadPool) {
        this.commonThreadPool = commonThreadPool;
    }

    public StandardTaskService getStandardTaskService() {
        return standardTaskService;
    }

    public void setStandardTaskService(StandardTaskService standardTaskService) {
        this.standardTaskService = standardTaskService;
    }
}
