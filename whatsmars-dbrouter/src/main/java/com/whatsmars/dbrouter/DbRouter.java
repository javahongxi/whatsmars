package com.whatsmars.dbrouter;

import org.springframework.util.StringUtils;

/**
 * Created by shenhongxi on 16/7/16.
 */
public class DbRouter {

    public void route(String fieldId) {
        if (StringUtils.isEmpty(fieldId)) {
            throw new IllegalArgumentException("dbsCount and tablesCount must be both positive!");
        } else {
            int routeFieldInt = fieldId.hashCode();
            // 分库又分表
            int dbs = 2;
            int tbs = 2;
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
