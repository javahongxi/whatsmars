package org.hongxi.summer.common;

/**
 * Created by shenhongxi on 2020/6/27.
 */
public enum URLParamType {

    version("version", SummerConstants.DEFAULT_VERSION),

    requestTimeout("requestTimeout", 200),
    /** request id from http interface **/
    requestIdFromClient("requestIdFromClient", 0),

    connectTimeout("connectTimeout", 1000),

    minWorkerThreads("minWorkerThreads", 20),

    maxWorkerThreads("maxWorkerThreads", 200),

    maxContentLength("maxContentLength", 10 * 1024 * 1024),

    maxServerConnections("maxServerConnections", 100000),

    minClientConnections("minClientConnections", 2),

    protocol("protocol", SummerConstants.PROTOCOL_SUMMER),
    path("path", ""),
    host("host", ""),
    port("port", 0),

    /**
     * multi referer share the same channel
     */
    shareChannel("shareChannel", false),
    asyncInitConnection("asyncInitConnection", false),
    fusingThreshold("fusingThreshold", 10),

    heartbeatFactory("heartbeatFactory", "org/hongxi/summer"),

    /************************** SPI start ******************************/

    serialization("serialization", "hessian2"),

    codec("codec", "org/hongxi/summer"),

    /************************** SPI end ******************************/

    group("group", "default_rpc"),

    nodeType("nodeType", SummerConstants.NODE_TYPE_SERVICE),

    gzip("gzip", false), // 是否开启gzip压缩
    minGzipSize("minGzipSize", 1000), // 进行gz压缩的最小数据大小。超过此阈值才进行gz压缩

    application("application", SummerConstants.FRAMEWORK_NAME),
    module("module", SummerConstants.FRAMEWORK_NAME),

    workerQueueSize("workerQueueSize", 0);

    private String name;
    private String value;
    private int intValue;
    private long longValue;
    private boolean boolValue;

    URLParamType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    URLParamType(String name, int intValue) {
        this.name = name;
        this.value = String.valueOf(intValue);
        this.intValue = intValue;
    }

    URLParamType(String name, long longValue) {
        this.name = name;
        this.value = String.valueOf(longValue);
        this.longValue = longValue;
    }

    URLParamType(String name, boolean boolValue) {
        this.name = name;
        this.value = String.valueOf(boolValue);
        this.boolValue = boolValue;
    }

    public String getName() {
        return name;
    }

    public String value() {
        return value;
    }

    public int intValue() {
        return intValue;
    }

    public long longValue() {
        return longValue;
    }

    public boolean boolValue() {
        return boolValue;
    }
}
