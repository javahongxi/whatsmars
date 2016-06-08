package com.itlong.whatsmars.earth.domain.query;

import java.util.Date;
import java.util.Map;

/**
 * Created by gongzaifei on 15/6/3.
 */
public class OrganizationWithdrawQuery extends BaseQuery{

    private Integer organizationId; // 机构ID

    private String  organizationName; //机构名称

    private String organizationCode; //机构编码

    private Integer status;//审批状态

    private Date beginDate;

    private String queryBeginDate;

    private Date endDate;

    private String queryEndDate;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("organizationId",this.organizationId);
        params.put("organizationName", this.organizationName);
        params.put("organizationCode", this.organizationCode);
        params.put("status", this.status);
        params.put("beginDate",this.beginDate);
        params.put("endDate",this.endDate);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("status=").append(status == null ? "" : status);
        sb.append("&organization_id=").append(organizationId == null ? "" : organizationId);
        sb.append("&organization_name=").append(organizationName == null ? "" : organizationName);
        sb.append("&organization_code=").append(organizationCode == null ? "" : organizationCode);
        sb.append("&begin_date=").append(this.queryBeginDate == null ? "" : this.queryBeginDate);
        sb.append("&end_date=").append(this.queryEndDate == null ? "" : this.queryEndDate);
        return sb.toString();
    }
}
