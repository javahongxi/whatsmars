package org.hongxi.whatsmars.boot.sample.web.filter;

/**
 * Created by shenhongxi on 2020/10/23.
 */
public class ResponseBodyHandler {

    public byte[] handle(byte[] body) {
        // you can encrypt the body if necessary
        return body;
    }
}
