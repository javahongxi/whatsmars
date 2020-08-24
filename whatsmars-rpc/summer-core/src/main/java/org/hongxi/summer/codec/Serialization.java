package org.hongxi.summer.codec;

import org.hongxi.summer.common.extension.Scope;
import org.hongxi.summer.common.extension.Spi;

import java.io.IOException;

/**
 * Created by shenhongxi on 2020/7/25.
 */
@Spi(scope = Scope.SINGLETON)
public interface Serialization {

    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException;

    byte[] serializeMulti(Object[] data) throws IOException;

    Object[] deserializeMulti(byte[] data, Class<?>[] classes) throws IOException;

    /**
     * serializaion的唯一编号，用于传输协议中指定序列化方式。每种序列化的编号必须唯一。
     * @return 由于编码规范限制，序列化方式最大支持32种，因此返回值必须在0-31之间。
     */
    int getSerializationNumber();
}
