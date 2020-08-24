package org.hongxi.summer.rpc;

import java.util.Map;

/**
 * Created by shenhongxi on 2020/6/14.
 */
public interface Response {

    /**
     * <pre>
     * 		如果 request 正常处理，那么会返回 Object value，而如果 request 处理有异常，那么 getValue 会抛出异常
     * </pre>
     *
     * @return
     * @throws RuntimeException
     */
    Object getValue();

    /**
     * 如果request处理有异常，那么调用该方法return exception 如果request还没处理完或者request处理正常，那么return null
     * <p>
     * <pre>
     * 		该方法不会阻塞，无论该request是处理中还是处理完成
     * </pre>
     *
     * @return
     */
    Exception getException();

    /**
     * 与 Request 的 requestId 相对应
     *
     * @return
     */
    long getRequestId();

    /**
     * 业务处理时间
     *
     * @return
     */
    long getProcessTime();

    /**
     * 业务处理时间
     *
     * @param time
     */
    void setProcessTime(long time);

    int getTimeout();

    Map<String, String> getAttachments();

    void setAttachment(String key, String value);

    /**
     * set the serialization number.
     * same to the protocol version, this value only used in server end for compatible.
     *
     * @param number
     */
    void setSerializationNumber(int number);

    int getSerializationNumber();
}
