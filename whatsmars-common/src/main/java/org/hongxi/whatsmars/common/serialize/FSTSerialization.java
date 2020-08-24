package org.hongxi.whatsmars.common.serialize;

import org.nustaq.serialization.FSTConfiguration;

import java.io.IOException;

/**
 * @author shenhongxi 2019/8/5
 */
public class FSTSerialization implements Serialization {

    private FSTConfiguration fst = FSTConfiguration.createStructConfiguration();

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return fst.asByteArray(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException, ClassNotFoundException {
        return (T) fst.asObject(bytes);
    }

    @Override
    public byte[] serializeMulti(Object[] data) throws IOException {
        return serialize(data);
    }

    @Override
    public Object[] deserializeMulti(byte[] data, Class<?>[] classes) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getSerializationNumber() {
        return 5;
    }
}
