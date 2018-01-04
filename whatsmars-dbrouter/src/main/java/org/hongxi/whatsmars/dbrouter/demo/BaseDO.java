package org.hongxi.whatsmars.dbrouter.demo;

import org.hongxi.whatsmars.dbrouter.DbContext;

/**
 * Created by shenhongxi on 16/7/16.
 */
public class BaseDO {

    private Long id;

    private String userId;

    private String tableIndex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTableIndex() {
        if (tableIndex != null && !"".equals(tableIndex)) return tableIndex;
        return DbContext.getTableIndex();
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
        DbContext.setTableIndex(tableIndex);
    }
}
