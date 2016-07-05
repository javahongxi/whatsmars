package com.itlong.whatsmars.spring;

/**
 * Created by shenhongxi on 2016/6/30.
 */
public class DbRouterTest {

    public static void main(String[] args) {
        String payid = "140331160123935469773";
        String resource = payid.substring(payid.length() - 4);
        int routeFieldInt = Integer.valueOf(resource).intValue();
        int dbs = 12;
        int tbs = 400;
        int mode = tbs * dbs;
        int dbIndex = routeFieldInt % mode / tbs;
        int tbIndex = routeFieldInt % tbs;
        System.out.println(dbIndex + "-->" + tbIndex);
    }
}
