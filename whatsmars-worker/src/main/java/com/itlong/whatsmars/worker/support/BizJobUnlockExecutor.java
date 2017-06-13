package com.itlong.whatsmars.worker.support;

import com.itlong.whatsmars.worker.base.AbstractJobExecutor;
import com.itlong.whatsmars.worker.base.DbContext;
import com.itlong.whatsmars.worker.base.SimpleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by shenhongxi on 16/7/16.
 * 参考TaskUnlockJobExecutor
 */
public class BizJobUnlockExecutor extends AbstractJobExecutor {

    private Logger log = LoggerFactory.getLogger(BizJobUnlockExecutor.class);

    private int tables;

    private String tableFormat = "0000";
    @Resource(name = "bizUnlockThreadPool")
    private ThreadPoolTaskExecutor bizUnlockThreadPool;
    @Resource(name = "bizUnlockService")
    private SimpleTaskService bizUnlockService;

    @Override
    public void execute() throws Exception {
        // 循环各表查询锁定任务
        for (int i = 0; i < tables; i++) {
            try {
                tableIndex = format(i, tableFormat);
                DbContext.setTableIndex(tableIndex);
                List list = null;
                if (list != null && list.size() > 0) {
                    int taskSize = list.size();
                    int batchTimes = computeBatchTimes(taskSize, batchSize);
                    for (int j = 0; j < batchTimes; j++) {
                        int from = computeBatchFromIndex(taskSize, batchSize, i);
                        int to = computeBatchToIndex(taskSize, batchSize, i);
                        this.execute(bizUnlockThreadPool, bizUnlockService, list.subList(from, to));
                    }
                } else {
                    log.info("没有被锁定的任务");
                }
            } catch (Exception e) {
                log.error("unlock error",e);
            }
        }
    }

    private String format(int num, String format){
        DecimalFormat df = new DecimalFormat();
        df.applyPattern(format);
        String ret=df.format(num);
        return ret;
    }

    public int getTables() {
        return tables;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }
}
