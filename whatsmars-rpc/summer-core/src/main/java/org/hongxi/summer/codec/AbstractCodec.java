package org.hongxi.summer.codec;

import org.apache.commons.lang3.StringUtils;
import org.hongxi.summer.common.extension.ExtensionLoader;
import org.hongxi.summer.exception.SummerErrorMsgConstants;
import org.hongxi.summer.exception.SummerFrameworkException;
import org.hongxi.summer.exception.SummerServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by shenhongxi on 2020/7/25.
 */
public abstract class AbstractCodec implements Codec {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCodec.class);

    protected static ConcurrentMap<Integer, String> serializations;

    protected void serialize(ObjectOutput output, Object message, Serialization serialization) throws IOException {
        if (message == null) {
            output.writeObject(null);
            return;
        }

        output.writeObject(serialization.serialize(message));
    }

    protected Object deserialize(byte[] value, Class<?> type, Serialization serialization) throws IOException {
        if (value == null) {
            return null;
        }

        return serialization.deserialize(value, type);
    }

    public ObjectOutput createOutput(OutputStream out) {
        try {
            return new ObjectOutputStream(out);
        } catch (Exception e) {
            throw new SummerFrameworkException(this.getClass().getSimpleName() + " createOutput error", e,
                    SummerErrorMsgConstants.FRAMEWORK_ENCODE_ERROR);
        }
    }

    public ObjectInput createInput(InputStream in) {
        try {
            return new ObjectInputStream(in);
        } catch (Exception e) {
            throw new SummerFrameworkException(this.getClass().getSimpleName() + " createInput error", e,
                    SummerErrorMsgConstants.FRAMEWORK_DECODE_ERROR);
        }
    }

    protected static synchronized void initAllSerialization() {
        if (serializations == null) {
            serializations = new ConcurrentHashMap<>();
            try {
                ExtensionLoader<Serialization> loader = ExtensionLoader.getExtensionLoader(Serialization.class);
                List<Serialization> exts = loader.getExtensions();
                for (Serialization s : exts) {
                    String old = serializations.put(s.getSerializationNumber(), loader.getSpiName(s.getClass()));
                    if (old != null) {
                        logger.warn("conflict serialization spi! serialization num :{}, old spi :{}, new spi :{}",
                                s.getSerializationNumber(), old, serializations.get(s.getSerializationNumber()));
                    }
                }
            } catch (Exception e) {
                logger.warn("init all serializations failed", e);
            }
        }
    }

    protected Serialization getSerializationByNum(int serializationNum) {
        if (serializations == null) {
            initAllSerialization();
        }
        String name = serializations.get(serializationNum);
        Serialization s = null;
        if (StringUtils.isNotBlank(name)) {
            s = ExtensionLoader.getExtensionLoader(Serialization.class).getExtension(name);
        }
        if (s == null) {
            throw new SummerServiceException("can not find serialization by number " + serializationNum);
        }
        return s;
    }
}
