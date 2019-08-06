package org.hongxi.whatsmars.netty.nio.http;

import org.hongxi.whatsmars.netty.nio.MessageReader;
import org.hongxi.whatsmars.netty.nio.MessageReaderFactory;

/**
 * Created by jjenkov on 18-10-2015.
 */
public class HttpMessageReaderFactory implements MessageReaderFactory {

    public HttpMessageReaderFactory() {
    }

    @Override
    public MessageReader createMessageReader() {
        return new HttpMessageReader();
    }
}
