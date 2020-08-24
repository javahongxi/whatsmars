package org.hongxi.summer.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.hongxi.summer.codec.Serialization;
import org.hongxi.summer.common.extension.SpiMeta;

import java.io.IOException;
import java.util.List;

/**
 * fastjson 序列化
 * 
 * <pre>
 * 对于嵌套场景无法支持
 * </pre>
 * 
 * Created by shenhongxi on 2020/7/28.
 * 
 */
@SpiMeta(name = "fastjson")
public class FastJsonSerialization implements Serialization {

    @Override
    public byte[] serialize(Object data) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(data);
        return out.toBytes("UTF-8");
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return JSON.parseObject(new String(data), clz);
    }

    @Override
    public byte[] serializeMulti(Object[] data) throws IOException {
        return serialize(data);
    }

    @Override
    public Object[] deserializeMulti(byte[] data, Class<?>[] classes) throws IOException {
         List<Object> list = JSON.parseArray(new String(data), classes);
         if (list != null) {
             return list.toArray();
         }
         return null;
    }

    @Override
    public int getSerializationNumber() {
        return 2;
    }
}
