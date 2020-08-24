package org.hongxi.whatsmars.common.serialize;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by shenhongxi on 2018/10/7.
 */
public class HessianSerialization implements Serialization {

    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.writeObject(obj);
        out.flush();
        return bos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(bytes));
        return (T) input.readObject(clz);
    }

    @Override
    public byte[] serializeMulti(Object[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        for(Object obj: data){
            out.writeObject(obj);
        }
        out.flush();
        return bos.toByteArray();
    }

    @Override
    public Object[] deserializeMulti(byte[] data, Class<?>[] classes) throws IOException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        Object[] objects = new Object[classes.length];
        for (int i = 0; i < classes.length; i++) {
            objects[i] = input.readObject(classes[i]);
        }
        return objects;
    }

    @Override
    public int getSerializationNumber() {
        return 0;
    }
}
