package com.itlong.whatsmars.worker.demo;

import com.itlong.whatsmars.worker.base.DbContext;
import com.itlong.whatsmars.worker.base.Task;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;

/**
 * Created by shenhongxi on 16/7/16.
 */
public class BizServiceImpl extends AbstractStandardTaskService {

    private static final Logger log = LoggerFactory.getLogger(BizServiceImpl.class);
    @Resource(name = "smsSender")
    private JmsTemplate jmsTemplate;
    @Resource(name = "taskMqExecutor")
    private ThreadPoolTaskExecutor taskMqExecutor;

    @Override
    public boolean process(Task task) {
        String taskTableIndex = DbContext.getTableIndex();
        try {
            String bizTableIndex = task.getTaskId().substring(6, task.getTaskId().length());
            DbContext.setTableIndex(bizTableIndex);
            JSONObject data = JSONObject.fromObject(task.getData());
            final long lastId = data.getInt("lastId");
            while (true) {
                List list = null;
                if (list == null || list.isEmpty()) {
                    log.info("任务全部处理完成，table:" + bizTableIndex);
                    return true;
                }

                for (final Object e : list) {
                    // 锁定
                    boolean lockResult = lock(e);
                    if (lockResult) {
                        taskMqExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                jmsTemplate.send(new MessageCreator() {
                                    @Override
                                    public Message createMessage(Session session) throws JMSException {
                                        JSONObject json = new JSONObject();
                                        json.put("bizId", "");
                                        return session.createTextMessage(json.toString());
                                    }
                                });
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            log.error("biz process error, taskId:" + task.getTaskId());
            return false;
        } finally {
            DbContext.setTableIndex(taskTableIndex);
        }
    }

    private boolean lock(Object e) {
        // ...
        return true;
    }
}
