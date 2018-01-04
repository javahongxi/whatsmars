package org.hongxi.whatsmars.earth.domain.query;

import java.util.Date;
import java.util.Map;

/**
 * Created by chenguang on 2015/6/3 0003.
 */
public class AccountFlowQuery extends BaseQuery {

    private Date beginDate;

    private String queryBeginDate;

    private Date endDate;

    private String queryEndDate;

    private Integer type; //交易类型

    private String phone;

    public Map<String, Object> build() {
        Map<String, Object> params = super.build();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        params.put("type", type);
        params.put("phone", phone);
        return params;
    }

    public String queryString() {
        StringBuffer sb = new StringBuffer();
        sb.append("begin_date=");
        sb.append(queryBeginDate == null ? "" : this.queryBeginDate);
        sb.append("&end_date=");
        sb.append(queryEndDate == null ? "" : this.queryEndDate);
        sb.append("&type=");
        sb.append(type == null ? "" : this.type);
        sb.append("&phone=");
        sb.append(phone == null ? "" : this.phone);
        return sb.toString();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getQueryBeginDate() {
        return queryBeginDate;
    }

    public void setQueryBeginDate(String queryBeginDate) {
        this.queryBeginDate = queryBeginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
