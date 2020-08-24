package org.hongxi.summer.common;

import java.io.File;

/**
 * Created by shenhongxi on 2020/6/26.
 */
public class SummerConstants {

    public static final String FRAMEWORK_NAME = "org/hongxi/summer";

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String PROTOCOL_SEPARATOR = "://";
    public static final String PATH_SEPARATOR = File.separator;

    public static final String NODE_TYPE_SERVICE = "service";

    /**
     * netty channel constants start
     */

    public static final short NETTY_MAGIC_TYPE = (short) 0xF1F1;

    public static final int NETTY_SHARE_CHANNEL_MIN_WORKER_THREADS = 40;
    public static final int NETTY_SHARE_CHANNEL_MAX_WORKER_THREADS = 800;
    public static final int NETTY_NOT_SHARE_CHANNEL_MIN_WORKER_THREADS = 20;
    public static final int NETTY_NOT_SHARE_CHANNEL_MAX_WORKER_THREADS = 200;

    public static final int NETTY_TIMEOUT_TIMER_PERIOD = 100;

    public static final String ASYNC_SUFFIX = "Async";// suffix for async call.

    public static final String DEFAULT_VERSION = "1.0";

    // netty client max concurrent request TODO 2W is suitable?
    public static final int NETTY_CLIENT_MAX_REQUEST = 20000;

    // ------------------ summer protocol constants -----------------
    public static final String SUMMER_GROUP = "S_g";
    public static final String SUMMER_VERSION = "S_v";
    public static final String SUMMER_PATH = "S_p";
    public static final String SUMMER_METHOD = "S_m";
    public static final String SUMMER_METHOD_DESC = "S_md";
    public static final String SUMMER_AUTH = "S_a";
    public static final String SUMMER_SOURCE = "S_s";// 调用方来源标识,等同与application
    public static final String SUMMER_MODULE = "S_mdu";
    public static final String SUMMER_PROXY_PROTOCOL = "S_pp";
    public static final String SUMMER_INFO_SIGN = "S_is";
    public static final String SUMMER_ERROR = "S_e";
    public static final String SUMMER_PROCESS_TIME = "S_pt";

    public static final String CONTENT_LENGTH = "Content-Length";

    public static final String PROTOCOL_INJVM = "injvm";
    public static final String PROTOCOL_SUMMER = "org/hongxi/summer";

    public static final String METHOD_CONFIG_PREFIX = "methodconfig.";

    public static final byte FLAG_REQUEST = 0x00;
    public static final byte FLAG_RESPONSE = 0x01;
    public static final byte FLAG_RESPONSE_VOID = 0x03;
    public static final byte FLAG_RESPONSE_EXCEPTION = 0x05;
    public static final byte FLAG_RESPONSE_ATTACHMENT = 0x07;
    public static final byte FLAG_OTHER = (byte) 0xFF;

}
