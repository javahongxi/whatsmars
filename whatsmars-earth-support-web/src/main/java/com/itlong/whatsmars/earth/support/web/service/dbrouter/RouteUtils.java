package com.itlong.whatsmars.earth.support.web.service.dbrouter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteUtils {
    private static final Logger log = LoggerFactory.getLogger(RouteUtils.class);
    private static final String encode = "utf-8";  
    private static final int resourceMax = 10000;  
  
    public RouteUtils() {  
    }  
  
    public static int getHashCodeBase64(String routeValue) {  
        int hashCode = 0;  
  
        try {  
            //String e = Base64Binrary.encodeBase64Binrary(routeValue.getBytes("utf-8"));
            //hashCode = Math.abs(e.hashCode());
        } catch (Exception var3) {  
            log.error("hashCode 失败", var3);  
        }  
  
        return hashCode;  
    }  
  
    public static int getResourceCode(String routeValue) {  
        int hashCode = getHashCodeBase64(routeValue);  
        int resourceCode = hashCode % 10000;  
        return resourceCode;  
    }  
  
    public static void main(String[] args) {  
        String payid = "140331160123935469773";  
        String resource = payid.substring(payid.length() - 4);  
        int routeFieldInt = Integer.valueOf(resource).intValue();  
        short mode = 1200;  
        int dbIndex = routeFieldInt % mode / 200;  
        int tbIndex = routeFieldInt % 200;  
        System.out.println(dbIndex + "-->" + tbIndex);  
    }  
}  