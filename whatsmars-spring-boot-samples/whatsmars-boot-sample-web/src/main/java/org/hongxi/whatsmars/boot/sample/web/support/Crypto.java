package org.hongxi.whatsmars.boot.sample.web.support;

/**
 * Created by shenhongxi on 2020/10/23.
 */
public class Crypto {

    public static byte[] decrypt(byte[] body) {
        // you need to decrypt the body if client encrypted the request body
        return body;
    }

    public static byte[] encrypt(byte[] body) {
        // you can encrypt the body if necessary
        return body;
    }
}
