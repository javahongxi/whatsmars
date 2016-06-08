package com.itlong.whatsmars.earth.domain.query;

import java.util.Date;
import java.util.Map;

/**
 * Created by shenhongxi on 15/5/14.
 */
public class OrganizationAccountFlowQuery extends BaseQuery {

    private Integer organizationId;

    //提交时间-开始
    private Date beginDate;

    private String queryBeginDate;

    private Integer type;

    //提交时间-结束
    private Date endDate;

    private String queryEndDate;

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

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        params.put("organizationId",organizationId);
        params.put("type",type);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("&organization_id=");
        sb.append(this.organizationId == null ? "" : this.organizationId);
        sb.append("&type=");
        sb.append(this.type == null ? "" : this.type);
        sb.append("&begin_date=");
        sb.append(this.queryBeginDate == null ? "" : this.queryBeginDate);
        sb.append("&end_date=");
        sb.append(this.queryEndDate == null ? "" : this.queryEndDate);
        return sb.toString();
    }

}
