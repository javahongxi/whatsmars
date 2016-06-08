package com.itlong.whatsmars.earth.domain.query;

import java.util.Date;
import java.util.Map;

/**
 * Created by chenguang on 2015/5/25 0025.
 */
public class LenderQuery extends BaseQuery {

    private Date beginDate;

    private Date endDate;

    private String queryBeginDate;

    private String queryEndDate;

    private String name;

    private String phone;

    private String realName;

    public Map<String, Object> build() {
        Map<String, Object> params = super.build();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        params.put("name", name);
        params.put("phone", phone);
        params.put("realName", realName);
        return params;
    }

    public String queryString() {
        StringBuffer sb = new StringBuffer();
        sb.append("begin_date=");
        sb.append(queryBeginDate == null ? "" : queryBeginDate);
        sb.append("&end_date=");
        sb.append(queryEndDate == null ? "" : queryEndDate);
        sb.append("&name=");
        sb.append(name == null ? "" : name);
        sb.append("&phone=");
        sb.append(phone == null ? "" : phone);
        sb.append("&real_name=");
        sb.append(realName == null ? "" : realName);
        return sb.toString();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getQueryBeginDate() {
        return queryBeginDate;
    }

    public void setQueryBeginDate(String queryBeginDate) {
        this.queryBeginDate = queryBeginDate;
    }

    public String getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}
