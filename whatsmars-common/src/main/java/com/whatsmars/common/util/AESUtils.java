package com.whatsmars.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by shenhongxi on 15/5/27.
 */
public class AESUtils {

    private static final String PADDING = "AES/ECB/PKCS5Padding";
    private static final String DEFAULT_ENCODING = "utf-8";

    /**
     * 加密
     *
     * @param data
     *            需要加密的内容
     * @param key
     *            加密密码
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        if(key.length!=16){
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat,"AES");
            Cipher cipher = Cipher.getInstance(PADDING);// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, seckey);// 初始化
            byte[] result = cipher.doFinal(data);
            return result; // 加密
        } catch (Exception e){
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 解密
     *
     * @param data
     *            待解密内容
     * @param key
     *            解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        if(key.length!=16){
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance(PADDING);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, seckey);// 初始化
            byte[] result = cipher.doFinal(data);
            return result; // 加密
        } catch (Exception e){
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static String encryptToBase64(String data, String key){
        try {
            byte[] valueByte = encrypt(data.getBytes(DEFAULT_ENCODING), key.getBytes(DEFAULT_ENCODING));
            return new Base64().encodeToString(valueByte);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }

    }

    public static String decryptFromBase64(String data, String key){
        try {
            byte[] originalData = new Base64().decode(data.getBytes());
            byte[] valueByte = decrypt(originalData, key.getBytes(DEFAULT_ENCODING));
            return new String(valueByte, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static String encryptWithKeyBase64(String data, String key){
        try {
            byte[] valueByte = encrypt(data.getBytes(DEFAULT_ENCODING), new Base64().decode(key.getBytes()));
            return new String(new Base64().encode(valueByte));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    public static String decryptWithKeyBase64(String data, String key){
        try {
            byte[] originalData = new Base64().decode(data.getBytes());
            byte[] valueByte = decrypt(originalData, new Base64().decode(key.getBytes()));
            return new String(valueByte, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static byte[] genarateRandomKey(){
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance(PADDING);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(" genarateRandomKey fail!", e);
        }
        SecureRandom random = new SecureRandom();
        keygen.init(random);
        Key key = keygen.generateKey();
        return key.getEncoded();
    }

    public static String genarateRandomKeyWithBase64(){
        return new String(new Base64().encode(genarateRandomKey()));
    }
}
