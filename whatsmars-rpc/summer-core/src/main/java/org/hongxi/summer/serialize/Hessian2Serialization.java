package org.hongxi.summer.serialize;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.hongxi.summer.codec.Serialization;
import org.hongxi.summer.common.extension.SpiMeta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * hession2 序列化，要求序列化的对象实现 java.io.Serializable 接口
 * 
 * Created by shenhongxi on 2020/7/28.
 *
 */
@SpiMeta(name = "hessian2")
public class Hessian2Serialization implements Serialization {

    @Override
    public byte[] serialize(Object data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.writeObject(data);
        out.flush();
        return bos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
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
