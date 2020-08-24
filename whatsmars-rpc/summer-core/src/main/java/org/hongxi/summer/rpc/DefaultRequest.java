package org.hongxi.summer.rpc;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2020/7/28.
 */
public class DefaultRequest implements Request, Serializable {
    private static final long serialVersionUID = -6525078483477733530L;

    private String interfaceName;
    private String methodName;
    private String parametersDesc;
    private Object[] arguments;
    private Map<String, String> attachments;
    private int retries = 0;
    private long requestId;
    private int serializationNumber = 0;// default serialization is hession2

    @Override
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getParametersDesc() {
        return parametersDesc;
    }

    public void setParametersDesc(String parametersDesc) {
        this.parametersDesc = parametersDesc;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public Map<String, String> getAttachments() {
        return attachments != null ? attachments : Collections.<String, String>emptyMap();
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    @Override
    public void setAttachment(String key, String value) {
        if (this.attachments == null) {
            this.attachments = new HashMap<>();
        }
        this.attachments.put(key, value);
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public int getRetries() {
        return retries;
    }

    @Override
    public void setRetries(int retries) {
        this.retries = retries;
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
    public String toString() {
        return interfaceName + "." + methodName + "(" + parametersDesc + ") requestId=" + requestId;
    }
}
