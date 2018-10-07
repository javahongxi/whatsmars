package org.hongxi.whatsmars.serialization;

import org.hongxi.whatsmars.codec.Serialization;

import java.io.IOException;

/**
 * Created by shenhongxi on 2018/10/7.
 */
public class DeserializableObject {
    private Serialization serialization;
    private byte[] objBytes;

    public DeserializableObject(Serialization serialization, byte[] objBytes) {
        this.serialization = serialization;
        this.objBytes = objBytes;
    }

    public <T> T deserialize(Class<T> clz) throws IOException {
        return serialization.deserialize(objBytes, clz);
    }

    public Object[] deserializeMulti(Class<?>[] paramTypes) throws IOException {
        Object[] ret = null;
        if (paramTypes != null && paramTypes.length > 0) {
            ret = serialization.deserializeMulti(objBytes, paramTypes);
        }
        return ret;
    }
}
