package com.whatsmars.mars001.domain.query;

import java.util.Map;

/**
 * Created by duanxiangchao on 2015/5/13.
 */
public class StudentBillStageQuery extends BaseQuery{

    private Integer biddingId;

    private Integer status;

    private Integer studentId;

    private Integer organizationId;

    private String phone;

    private String studentName;

    public Integer getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Integer biddingId) {
        this.biddingId = biddingId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("biddingId",biddingId);
        params.put("status",status);
        params.put("studentId",studentId);
        params.put("studentName",studentName);
        params.put("organizationId",organizationId);
        params.put("phone",phone);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("&biddingId=");
        sb.append(this.biddingId == null ? "" : this.biddingId);
        sb.append("&status=");
        sb.append(this.status == null ? "" : this.status);
        sb.append("&studentId=");
        sb.append(this.studentId == null ? "" : this.studentId);
        sb.append("&studentName=");
        sb.append(this.studentName == null ? "" : this.studentName);
        sb.append("&organizationId=");
        sb.append(this.organizationId == null ? "" : this.organizationId);
        sb.append("&phone=");
        sb.append(this.phone == null ? "" : this.phone);
        return sb.toString();
    }

}
