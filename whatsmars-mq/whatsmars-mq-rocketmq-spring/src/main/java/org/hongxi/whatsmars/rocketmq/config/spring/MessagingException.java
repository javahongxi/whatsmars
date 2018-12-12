package org.hongxi.whatsmars.rocketmq.config.spring;

import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.help.FAQUrl;

/**
 * Created by shenhongxi on 2018/12/12.
 */
public class MessagingException extends RuntimeException {

    private static final long serialVersionUID = -5758410930844185841L;
    private int responseCode;
    private String errorMessage;

    public MessagingException(String errorMessage, Throwable cause) {
        super(FAQUrl.attachDefaultURL(errorMessage), cause);
        this.responseCode = -1;
        this.errorMessage = errorMessage;
    }

    public MessagingException(int responseCode, String errorMessage) {
        super(FAQUrl.attachDefaultURL("CODE: " + UtilAll.responseCode2String(responseCode) + "  DESC: "
                + errorMessage));
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public MessagingException setResponseCode(final int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
