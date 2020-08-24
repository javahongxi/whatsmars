package org.hongxi.summer.transport.netty;

import org.hongxi.summer.common.extension.SpiMeta;
import org.hongxi.summer.rpc.URL;
import org.hongxi.summer.transport.Client;
import org.hongxi.summer.transport.MessageHandler;
import org.hongxi.summer.transport.Server;
import org.hongxi.summer.transport.support.AbstractEndpointFactory;

/**
 * Created by shenhongxi on 2020/7/31.
 */
@SpiMeta(name = "summer")
public class NettyEndpointFactory extends AbstractEndpointFactory {
    @Override
    protected Server innerCreateServer(URL url, MessageHandler messageHandler) {
        return new NettyServer(url, messageHandler);
    }

    @Override
    protected Client innerCreateClient(URL url) {
        return new NettyClient(url);
    }
}
