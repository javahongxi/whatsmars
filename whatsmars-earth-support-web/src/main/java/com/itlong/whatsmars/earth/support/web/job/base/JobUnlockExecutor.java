package com.itlong.whatsmars.earth.support.web.job.base;

import com.itlong.whatsmars.earth.support.web.service.SimpleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by shenhongxi on 2016/7/12.
 */
public class JobUnlockExecutor extends AbstractJobExecutor {

    private Logger log = LoggerFactory.getLogger(JobUnlockExecutor.class);

    /** 配置任务最大锁定时间（秒）*/
    private int splitSeconds = 60 * 5;
    /** 配置失败重试次数 */
    private int retries = 6;
    /** 配置每次批处理个数 */
    private int batchSize = 100;
    /** 配置数据库个数 */
    private int dbs;
    @Autowired
    private ThreadPoolTaskExecutor taskUnlockThreadPool;
    @Resource(name="taskUnlockService")
    private SimpleTaskService taskUnlockService;

    @Override
    public void execute() throws Exception {
        try {
            DbContext.setTableIndex(tableIndex);
            for (int i = 1; i <= dbs; i++) {
                String dbKey = "db" + i;
                DbContext.setDbKey(dbKey);
                List<Task> tasks = taskBaseService.findLocked(splitSeconds, retries);
                if (tasks != null && tasks.size() > 0) {
                    int size = tasks.size();
                    log.info("取到被锁定任务条数：" + size + ",dbKey:" + dbKey);
                    int batchTimes = computeBatchTimes(size, batchSize);
                    for (int j = 0; j < batchTimes; j++) {
                        int from = computeBatchFromIndex(size, batchSize, j);
                        int to = computeBatchToIndex(size, batchSize, j);
                        this.execute(taskUnlockThreadPool, taskUnlockService, tasks.subList(from, to));
                    }
                } else {
                    log.info("任务调度--没有被锁定的任务,dbkey:" + dbKey);
                }
            }
        } catch (Exception e) {
            log.error("任务调度--定期重置被锁定的任务异常", e);
        }
    }

    public Integer getDbs() {
        return dbs;
    }

    public void setDbs(Integer dbs) {
        this.dbs = dbs;
    }

    public Integer getSplitSeconds() {
        return splitSeconds;
    }

    public void setSplitSeconds(Integer splitSeconds) {
        this.splitSeconds = splitSeconds;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }
}
