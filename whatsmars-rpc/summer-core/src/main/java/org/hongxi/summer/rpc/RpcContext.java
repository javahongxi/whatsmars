package org.hongxi.summer.rpc;

import org.hongxi.summer.common.URLParamType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2020/6/26.
 */
public class RpcContext {
    private static final ThreadLocal<RpcContext> LOCAL_CONTEXT = ThreadLocal.withInitial(() -> new RpcContext());
    private Map<Object, Object> attributes = new HashMap<>();
    private Map<String, String> attachments = new HashMap<>();
    private Request request;
    private Response response;
    private String clientRequestId;

    public static RpcContext getContext() {
        return LOCAL_CONTEXT.get();
    }

    public static void destroy() {
        LOCAL_CONTEXT.remove();
    }

    /**
     * init new rpcContext with request
     *
     * @param request
     * @return
     */
    public static RpcContext init(Request request) {
        RpcContext context = new RpcContext();
        if (request != null) {
            context.setRequest(request);
            context.setClientRequestId(
                    request.getAttachments().get(URLParamType.requestIdFromClient.getName()));
        }
        LOCAL_CONTEXT.set(context);
        return context;
    }

    public String getRequestId() {
        if (clientRequestId != null) return clientRequestId;
        if (request != null) return String.valueOf(request.getRequestId());
        return null;
    }

    public void putAttribute(Object key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(Object key) {
        return attributes.get(key);
    }

    public void removeAttribute(Object key) {
        attributes.remove(key);
    }

    public Map<Object, Object> getAttributes() {
        return attributes;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getClientRequestId() {
        return clientRequestId;
    }

    public void setClientRequestId(String clientRequestId) {
        this.clientRequestId = clientRequestId;
    }
}
