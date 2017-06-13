package com.itlong.whatsmars.dbrouter;

import org.springframework.util.StringUtils;

/**
 * Created by shenhongxi on 16/7/16.
 */
public class DbRouter {

    public void route(String fieldId) {
        if(StringUtils.isEmpty(fieldId)) {
            throw new IllegalArgumentException("dbsCount and tablesCount must be both positive!");
        } else {
            // base64编码得到的字符串取hashcode
            int routeFieldInt = RouteUtils.getResourceCode(fieldId);
            // 分库又分表
            int dbs = 6;
            int tbs = 200;
            int mode = dbs * tbs;
            Integer dbIndex = routeFieldInt % mode / tbs;
            Integer tableIndex = routeFieldInt % tbs;
            // tableIndex格式化
            // dbIndex -> dbKey;
            DbContext.setTableIndex(tableIndex.toString());
            DbContext.setDbKey(dbIndex.toString());
        }
    }

}
