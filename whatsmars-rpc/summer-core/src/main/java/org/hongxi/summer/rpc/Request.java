package org.hongxi.summer.rpc;

import java.util.Map;

/**
 * Created by shenhongxi on 2020/6/14.
 */
public interface Request {

    /**
     * service interface
     *
     * @return
     */
    String getInterfaceName();

    /**
     * service method name
     *
     * @return
     */
    String getMethodName();

    /**
     * service method param desc (sign)
     *
     * @return
     */
    String getParametersDesc();

    /**
     * service method param
     *
     * @return
     */
    Object[] getArguments();

    /**
     * get framework param
     *
     * @return
     */
    Map<String, String> getAttachments();

    /**
     * set framework param
     *
     * @return
     */
    void setAttachment(String name, String value);

    /**
     * request id
     *
     * @return
     */
    long getRequestId();

    /**
     * retries
     *
     * @return
     */
    int getRetries();

    /**
     * set retries
     */
    void setRetries(int retries);

    /**
     * set the serialization number.
     * same to the protocol version, this value only used in server end for compatible.
     *
     * @param number
     */
    void setSerializationNumber(int number);

    int getSerializationNumber();
}
