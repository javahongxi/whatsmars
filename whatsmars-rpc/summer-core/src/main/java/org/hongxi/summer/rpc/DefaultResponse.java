package org.hongxi.summer.rpc;

import org.apache.commons.lang3.tuple.Pair;
import org.hongxi.summer.exception.SummerServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by shenhongxi on 2020/7/25.
 */
public class DefaultResponse implements Response, Callbackable, Serializable {
    private static final long serialVersionUID = -46598719225168485L;

    private static final Logger logger = LoggerFactory.getLogger(DefaultResponse.class);

    private Object value;
    private Exception exception;
    private long requestId;
    private long processTime;
    private int timeout;
    private Map<String, String> attachments;// rpc协议版本兼容时可以回传一些额外的信息
    private int serializationNumber = 0;// default serialization is hession2
    private List<Pair<Runnable, Executor>> taskList = new ArrayList<>();
    private AtomicBoolean isFinished = new AtomicBoolean();

    public DefaultResponse() {
    }

    public DefaultResponse(long requestId) {
        this.requestId = requestId;
    }

    public DefaultResponse(Response response) {
        this.value = response.getValue();
        this.exception = response.getException();
        this.requestId = response.getRequestId();
        this.processTime = response.getProcessTime();
        this.timeout = response.getTimeout();
        this.serializationNumber = response.getSerializationNumber();
        this.attachments = response.getAttachments();
    }

    public DefaultResponse(Object value) {
        this.value = value;
    }

    public DefaultResponse(Object value, long requestId) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        if (exception != null) {
            throw (exception instanceof RuntimeException) ? (RuntimeException) exception : new SummerServiceException(
                    exception.getMessage(), exception);
        }

        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public long getProcessTime() {
        return processTime;
    }

    @Override
    public void setProcessTime(long time) {
        this.processTime = time;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    @Override
    public Map<String, String> getAttachments() {
        return attachments != null ? attachments : Collections.emptyMap();
    }

    @Override
    public void setAttachment(String key, String value) {
        if (attachments == null) {
            attachments = new HashMap<>();
        }
        attachments.put(key, value);
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public void setSerializationNumber(int number) {
        this.serializationNumber = number;
    }

    @Override
    public int getSerializationNumber() {
        return serializationNumber;
    }

    @Override
    public void addFinishCallback(Runnable runnable, Executor executor) {
        if (!isFinished.get()) {
            taskList.add(Pair.of(runnable, executor));
        }
    }

    @Override
    public void onFinish() {
        if (!isFinished.compareAndSet(false, true)) {
            return;
        }
        for (Pair<Runnable, Executor> pair : taskList) {
            Runnable runnable = pair.getKey();
            Executor executor = pair.getValue();
            if (executor == null) {
                runnable.run();
            } else {
                try {
                    executor.execute(runnable);
                } catch (Exception e) {
                    logger.error("Callbackable response exec callback task error", e);
                }
            }
        }
    }
}
