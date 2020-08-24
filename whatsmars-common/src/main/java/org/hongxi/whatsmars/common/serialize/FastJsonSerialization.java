package org.hongxi.whatsmars.common.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * fastjson 序列化
 * 
 * <pre>
 * 对于嵌套场景无法支持
 * </pre>
 *
 * @author shenhongxi 2019/8/5
 */
public class FastJsonSerialization implements Serialization {

    private final static Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(Object obj) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(obj);
        return out.toBytes(CHARSET_UTF8);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        return JSON.parseObject(new String(bytes, CHARSET_UTF8), clz);
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
