package com.ixhong.base;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by shenhongxi on 15/7/24.
 */
public class PropertiesUtils {

    private static  Properties properties = new Properties();

    static {
        try {
            InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream("conf.properties");
            properties.load(in);
        } catch (Exception e) {}
    }

    public static void main(String[] args) {
        System.out.print(properties.getProperty("max"));
    }
}
