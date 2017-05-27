package com.itlong.whatsmars.earth.support.web.service.dbrouter;

/**
 * Created by shenhongxi on 16/7/16.
 */
public class BaseDO {

    private String userId;

    private String tableIndex;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
    }
}
