package org.hongxi.summer.transport;

import org.hongxi.summer.rpc.Request;

/**
 * Created by shenhongxi on 2020/7/28.
 */
public interface Client extends Endpoint {

    void heartbeat(Request request);
}
