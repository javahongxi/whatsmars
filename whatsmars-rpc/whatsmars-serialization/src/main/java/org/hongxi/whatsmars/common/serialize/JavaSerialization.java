package org.hongxi.whatsmars.common.serialize;

import java.io.*;

/**
 * Created on 2019/8/5.
 *
 * @author shenhongxi
 */
public class JavaSerialization implements Serialization {

    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        return baos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }

    @Override
    public byte[] serializeMulti(Object[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        for (Object obj : data) {
            oos.writeObject(obj);
        }
        return baos.toByteArray();
    }

    @Override
    public Object[] deserializeMulti(byte[] data, Class<?>[] classes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object[] objects = new Object[classes.length];
        for (int i = 0; i < classes.length; i++) {
            objects[i] = ois.readObject();
        }
        return objects;
    }

    @Override
    public int getSerializationNumber() {
        return 1;
    }
}
