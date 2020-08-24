package org.hongxi.summer.common.util;

import org.apache.commons.lang3.StringUtils;
import org.hongxi.summer.common.SummerConstants;
import org.hongxi.summer.common.URLParamType;
import org.hongxi.summer.rpc.DefaultResponse;
import org.hongxi.summer.rpc.Request;
import org.hongxi.summer.rpc.URL;

/**
 * Created by shenhongxi on 2020/7/25.
 */
public class SummerFrameworkUtils {

    public static String removeAsyncSuffix(String path) {
        if (path != null && path.endsWith(SummerConstants.ASYNC_SUFFIX)) {
            return path.substring(0, path.length() - SummerConstants.ASYNC_SUFFIX.length());
        }
        return path;
    }

    public static DefaultResponse buildErrorResponse(Request request, Exception e) {
        return buildErrorResponse(request.getRequestId(), e);
    }

    public static DefaultResponse buildErrorResponse(long requestId, Exception e) {
        DefaultResponse response = new DefaultResponse();
        response.setRequestId(requestId);
        response.setException(e);
        return response;
    }

    /**
     * protocol key: protocol://host:port/group/interface/version
     *
     * @param url
     * @return
     */
    public static String getProtocolKey(URL url) {
        StringBuilder key = new StringBuilder();
        key.append(url.getProtocol());
        key.append(SummerConstants.PROTOCOL_SEPARATOR);
        key.append(url.getServerPortStr());
        key.append(SummerConstants.PATH_SEPARATOR);
        key.append(url.getGroup());
        key.append(SummerConstants.PATH_SEPARATOR);
        key.append(url.getPath());
        key.append(SummerConstants.PATH_SEPARATOR);
        key.append(url.getVersion());
        return key.toString();
    }

    /**
     * 判断url:source和url:target是否可以使用共享的service channel(port) 对外提供服务
     * <p>
     * <pre>
     * 		1） protocol
     * 		2） codec
     * 		3） serialize
     * 		4） maxContentLength
     * 		5） maxServerConnection
     * 		6） maxWorkerThread
     * 		7） workerQueueSize
     * 		8） heartbeatFactory
     * </pre>
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean checkIfCanShareServiceChannel(URL source, URL target) {
        if (!StringUtils.equals(source.getProtocol(), target.getProtocol())) {
            return false;
        }

        if (!StringUtils.equals(source.getParameter(URLParamType.codec.getName()),
                target.getParameter(URLParamType.codec.getName()))) {
            return false;
        }

        if (!StringUtils.equals(source.getParameter(URLParamType.serialization.getName()),
                target.getParameter(URLParamType.serialization.getName()))) {
            return false;
        }

        if (!StringUtils.equals(source.getParameter(URLParamType.maxContentLength.getName()),
                target.getParameter(URLParamType.maxContentLength.getName()))) {
            return false;
        }

        if (!StringUtils.equals(source.getParameter(URLParamType.maxServerConnections.getName()),
                target.getParameter(URLParamType.maxServerConnections.getName()))) {
            return false;
        }

        if (!StringUtils.equals(source.getParameter(URLParamType.maxWorkerThreads.getName()),
                target.getParameter(URLParamType.maxWorkerThreads.getName()))) {
            return false;
        }

        if (!StringUtils.equals(source.getParameter(URLParamType.workerQueueSize.getName()),
                target.getParameter(URLParamType.workerQueueSize.getName()))) {
            return false;
        }

        return StringUtils.equals(source.getParameter(URLParamType.heartbeatFactory.getName()),
                target.getParameter(URLParamType.heartbeatFactory.getName()));

    }

    /**
     * 输出请求的关键信息： requestId=** interface=** method=**(**)
     *
     * @param request
     * @return
     */
    public static String toString(Request request) {
        return "requestId=" + request.getRequestId() +
                " interface=" + request.getInterfaceName() +
                " method=" + request.getMethodName()
                + "(" + request.getParametersDesc() + ")";
    }
}
