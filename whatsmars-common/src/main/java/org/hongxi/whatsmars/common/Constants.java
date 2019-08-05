package org.hongxi.whatsmars.common;

import java.util.regex.Pattern;

/**
 * Created on 2019/8/5.
 *
 * @author shenhongxi
 */
public class Constants {

    public static final String COMMA_SEPARATOR = ",";
    public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
    public static final String PROTOCOL_SEPARATOR = "://";
    public static final String PATH_SEPARATOR = "/";
}
