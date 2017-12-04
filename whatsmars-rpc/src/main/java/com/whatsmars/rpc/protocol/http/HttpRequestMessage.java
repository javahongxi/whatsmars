package com.whatsmars.rpc.protocol.http;

import java.util.Iterator;
import java.util.Map.Entry;

public class HttpRequestMessage extends HttpMessage {

    private String requestProtocol;
    private String requestUrl;
    private int errorCode = 0;

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getRequestProtocol() {
        return this.requestProtocol;
    }

    public void setRequestProtocol(String requestProtocol) {
        this.requestProtocol = requestProtocol;
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n\r\n");
        sb.append("\r\n");
        sb.append(this.requestProtocol).append(" ").append(this.requestUrl).append(" ").append(this.version).append("\r\n");

        Iterator<Entry<String, String>> iterator = this.messageHeader.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        sb.append("\r\n\r\n");
        sb.append(this.messageBody);
        sb.append("\r\n\r\n");
        return sb.toString();
    }
}

