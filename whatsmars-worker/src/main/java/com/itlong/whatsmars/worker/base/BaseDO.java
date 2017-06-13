package com.itlong.whatsmars.worker.base;

/**
 * Created by shenhongxi on 2016/7/11.
 */
public class BaseDO {

    private String phone; // 手机号码

    private String tableIndex;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTableIndex() {
        tableIndex = DbContext.getTableIndex();
        return tableIndex;
    }

    public void setTableIndex(String tableIndex) {
        this.tableIndex = tableIndex;
    }
}
