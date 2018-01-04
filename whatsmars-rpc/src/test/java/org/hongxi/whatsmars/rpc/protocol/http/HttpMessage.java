package org.hongxi.whatsmars.rpc.protocol.http;

import java.util.HashMap;
import java.util.Map;

public abstract class HttpMessage {

    String version = "HTTP/1.1";

    String messageBody = "";

    Map<String, String> messageHeader = new HashMap<String, String>();

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, String> getMessageHeader() {
        return this.messageHeader;
    }

    public void setMessageHeader(Map<String, String> messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getRequestHeader(String key) {
        return (String) this.messageHeader.get(key);
    }

    public void addRequestHeader(String key, String value) {
        this.messageHeader.put(key, value);
    }
}

