package org.hongxi.summer.rpc;

import org.hongxi.summer.common.FutureState;
import org.hongxi.summer.common.util.SummerFrameworkUtils;
import org.hongxi.summer.exception.SummerErrorMsgConstants;
import org.hongxi.summer.exception.SummerFrameworkException;
import org.hongxi.summer.exception.SummerServiceException;
import org.hongxi.summer.serialize.DeserializableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by shenhongxi on 2020/8/23.
 */
public class DefaultResponseFuture implements ResponseFuture {
    private static final Logger logger = LoggerFactory.getLogger(DefaultResponseFuture.class);

    protected final Object lock = new Object();
    protected volatile FutureState state = FutureState.DOING;
    protected Object result = null;
    protected Exception exception = null;

    protected long createTime = System.currentTimeMillis();
    protected int timeout = 0;
    protected long processTime = 0;
    protected Request request;
    protected List<FutureListener> listeners;
    protected URL serverUrl;
    protected Class returnType;
    private Map<String, String> attachments;// rpc协议版本兼容时可以回传一些额外的信息

    public DefaultResponseFuture(Request requestObj, int timeout, URL serverUrl) {
        this.request = requestObj;
        this.timeout = timeout;
        this.serverUrl = serverUrl;
    }

    @Override
    public void onSuccess(Response response) {
        this.result = response.getValue();
        this.processTime = response.getProcessTime();
        this.attachments = response.getAttachments();

        done();
    }

    @Override
    public void onFailure(Response response) {
        this.exception = response.getException();
        this.processTime = response.getProcessTime();

        done();
    }

    @Override
    public Object getValue() {
        synchronized (lock) {
            if (!isDoing()) {
                return getValueOrThrowable();
            }

            if (timeout <= 0) {
                try {
                    lock.wait();
                } catch (Exception e) {
                    cancel(new SummerServiceException(this.getClass().getName() +
                            " getValue InterruptedException : "
                            + SummerFrameworkUtils.toString(request) +
                            " cost=" + (System.currentTimeMillis() - createTime), e));
                }

                return getValueOrThrowable();
            } else {
                long waitTime = timeout - (System.currentTimeMillis() - createTime);

                if (waitTime > 0) {
                    for (; ; ) {
                        try {
                            lock.wait(waitTime);
                        } catch (InterruptedException e) {
                        }

                        if (!isDoing()) {
                            break;
                        } else {
                            waitTime = timeout - (System.currentTimeMillis() - createTime);
                            if (waitTime <= 0) {
                                break;
                            }
                        }
                    }
                }

                if (isDoing()) {
                    timeoutSoCancel();
                }
            }
            return getValueOrThrowable();
        }
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public boolean cancel() {
        Exception e = new SummerServiceException(this.getClass().getName() +
                        " task cancel: serverPort=" + serverUrl.getServerPortStr() + " "
                        + SummerFrameworkUtils.toString(request) +
                " cost=" + (System.currentTimeMillis() - createTime));
        return cancel(e);
    }

    protected boolean cancel(Exception e) {
        synchronized (lock) {
            if (!isDoing()) {
                return false;
            }

            state = FutureState.CANCELLED;
            exception = e;
            lock.notifyAll();
        }

        notifyListeners();
        return true;
    }

    @Override
    public boolean isCancelled() {
        return state.isCancelledState();
    }

    @Override
    public boolean isDone() {
        return state.isDoneState();
    }

    @Override
    public boolean isSuccess() {
        return isDone() && (exception == null);
    }

    @Override
    public void addListener(FutureListener listener) {
        if (listener == null) {
            throw new NullPointerException("FutureListener is null");
        }

        boolean notifyNow = false;
        synchronized (lock) {
            if (!isDoing()) {
                notifyNow = true;
            } else {
                if (listeners == null) {
                    listeners = new ArrayList<>(1);
                }

                listeners.add(listener);
            }
        }

        if (notifyNow) {
            notifyListener(listener);
        }
    }

    @Override
    public long getCreateTime() {
        return createTime;
    }

    @Override
    public void setReturnType(Class<?> clazz) {
        this.returnType = clazz;
    }

    public Object getRequestObj() {
        return request;
    }

    public FutureState getState() {
        return state;
    }

    private void timeoutSoCancel() {
        this.processTime = System.currentTimeMillis() - createTime;

        synchronized (lock) {
            if (!isDoing()) {
                return;
            }

            state = FutureState.CANCELLED;
            exception = new SummerServiceException(
                    this.getClass().getName() +
                            " request timeout: serverPort=" + serverUrl.getServerPortStr()
                            + " " + SummerFrameworkUtils.toString(request) +
                            " cost=" + (System.currentTimeMillis() - createTime),
                            SummerErrorMsgConstants.SERVICE_TIMEOUT);

            lock.notifyAll();
        }

        notifyListeners();
    }

    private void notifyListeners() {
        if (listeners != null) {
            for (FutureListener listener : listeners) {
                notifyListener(listener);
            }
        }
    }

    private void notifyListener(FutureListener listener) {
        try {
            listener.operationComplete(this);
        } catch (Throwable t) {
            logger.error(this.getClass().getName() + " notifyListener Error: " + listener.getClass().getSimpleName(), t);
        }
    }

    private boolean isDoing() {
        return state.isDoingState();
    }

    protected boolean done() {
        synchronized (lock) {
            if (!isDoing()) {
                return false;
            }

            state = FutureState.DONE;
            lock.notifyAll();
        }

        notifyListeners();
        return true;
    }

    @Override
    public long getRequestId() {
        return this.request.getRequestId();
    }

    private Object getValueOrThrowable() {
        if (exception != null) {
            throw (exception instanceof RuntimeException) ?
                    (RuntimeException) exception :
                    new SummerServiceException(exception.getMessage(), exception);
        }
        if (result != null && returnType != null && result instanceof DeserializableObject) {
            try {
                result = ((DeserializableObject) result).deserialize(returnType);
            } catch (IOException e) {
                logger.error("deserialize response value fail! return type: {}", returnType, e);
                throw new SummerFrameworkException("deserialize return value fail! deserialize type:" + returnType, e);
            }
        }
        return result;
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
        return attachments != null ? attachments : Collections.<String, String>emptyMap();
    }

    @Override
    public void setAttachment(String key, String value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        this.attachments.put(key, value);
    }

    @Override
    public void setSerializationNumber(int number) {

    }

    @Override
    public int getSerializationNumber() {
        return 0;
    }
}
