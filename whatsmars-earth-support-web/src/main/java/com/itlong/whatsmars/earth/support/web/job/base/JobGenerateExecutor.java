package com.itlong.whatsmars.earth.support.web.job.base;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shenhongxi on 2016/7/12.
 */
public class JobGenerateExecutor extends AbstractJobExecutor {

    private static final Logger log = LoggerFactory.getLogger(JobGenerateExecutor.class);

    private int dbs;

    private int tables;

    private String tableFormat = "0000";
    @Autowired
    private CacheService cacheService;

    private static final int cacheTime = 60*60*24-60*20; // 缓存时间23小时40分

    @Override
    public void execute() throws Exception {
        log.info("shard job start!");
        String taskType = new Integer(tableIndex).toString();
        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String _tableIndex; // 0000
        String taskId = "";
        String successFlag = "y";
        String shardAllCacheKey = "shardAllJob_" + today;
        if (successFlag.equals(cacheService.get(shardAllCacheKey))) {
            log.info("任务已经全部生成，date:" + today);
        } else {
            boolean allSuccess = true;
            DbContext.setTableIndex(tableIndex);
            for (int i = 1; i <= dbs; i++) {
                String dbKey = "db" + i;
                DbContext.setDbKey(dbKey);
                for (int j = 0; j < tables; j++) {
                    try {
                        _tableIndex = format(j, tableFormat);
                        taskId = today + _tableIndex;
                        String shardSingleCacheKey = "shardSingleJob_" + dbKey + "_" + taskId;
                        if (successFlag.equals(cacheService.get(shardSingleCacheKey))) {
                            log.info("此任务已经生成，dbKey:" + dbKey + ",taskId:" + taskId);
                        } else {
                            Task task = new Task();
                            task.setTaskId(taskId);
                            task.setType(taskType);
                            JSONObject json = new JSONObject();
                            json.put("lastId", 0L);
                            task.setData(json.toString());
                            taskBaseService.add(task);
                            log.info("生成任务成功。dbKey:" + dbKey + ",tableIndex:" + tableIndex + ",taskId:" + taskId);
                        }
                    } catch (Exception e) {
                        if (e.getMessage() != null && (e.getMessage().indexOf("Duplicate") >= 0 || e.getMessage().indexOf("UNIQUE KEY") >= 0
                                || e.getMessage().indexOf("PRIMARY KEY") >= 0)) { // 任务重复，视为成功
                            log.info("生成任务重复。taskType:" + taskType + "，taskId:" + taskId);
                        }else{
                            log.error("生成任务异常。taskType:" + taskType + "，taskId:" + taskId, e);
                            allSuccess = false;
                        }
                    }
                }
            }
            if (allSuccess) {
                cacheService.set(shardAllCacheKey, successFlag, cacheTime);
            }
        }
    }

    private String format(int num, String format){
        DecimalFormat df = new DecimalFormat();
        df.applyPattern(format);
        return df.format(num);
    }

    public int getDbs() {
        return dbs;
    }

    public void setDbs(int dbs) {
        this.dbs = dbs;
    }

    public int getTables() {
        return tables;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }

    public String getTableFormat() {
        return tableFormat;
    }

    public void setTableFormat(String tableFormat) {
        this.tableFormat = tableFormat;
    }
}
