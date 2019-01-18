package org.hongxi.whatsmars.earth.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;


public class DESUtils {
    private static final String DES = "DES";
    private static final String PADDING = "DES/ECB/PKCS5Padding";
    private static final String DEFAULT_ENCODING = "utf-8";

    public final static String encrypt(String code, String key) {
        try {
            return Base64.encodeBase64String(encrypt(code.getBytes(DEFAULT_ENCODING), key
                    .getBytes(DEFAULT_ENCODING)));
        } catch (Exception e) {
            //
        }
        return null;

    }

    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);

    }

    public final static String decrypt(String data, String key) {
        try {
            //base64,default-charset is UTF-8
            return new String(decrypt(Base64.decodeBase64(data),
                    key.getBytes(DEFAULT_ENCODING)), DEFAULT_ENCODING);

        } catch (Exception e) {
            //
        }
        return null;
    }

    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);
    }


}
