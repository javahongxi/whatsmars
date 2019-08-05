package org.hongxi.whatsmars.common.serialize;

import org.msgpack.MessagePack;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;

/**
 * Created on 2019/8/5.
 *
 * @author shenhongxi
 */
public class MsgpackSerialization implements Serialization {

    private MessagePack messagePack = new MessagePack();

    @Override
    public byte[] serialize(Object obj) throws IOException {
        return messagePack.write(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException, ClassNotFoundException {
        return messagePack.read(bytes, clz);
    }

    @Override
    public byte[] serializeMulti(Object[] data) throws IOException {
        return serialize(data);
    }

    @Override
    public Object[] deserializeMulti(byte[] data, Class<?>[] classes) {
        return null;
    }

    @Override
    public int getSerializationNumber() {
        return 3;
    }
}
