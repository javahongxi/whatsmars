package com.whatsmars.mars001.domain.query;

import java.util.Date;
import java.util.Map;

/**
 * Created by shenhongxi on 15/5/14.
 */
public class StudentAccountFlowQuery extends BaseQuery {

    //提交时间-开始
    private Date beginDate;

    private String queryBeginDate;

    //交易类型
    private Integer type;

    //提交时间-结束
    private Date endDate;

    private String queryEndDate;

    private Integer studentId;

    private String studentName;

    private String studentPhone;

    private String organizationName;

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

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        params.put("type",type);
        params.put("studentId", studentId);
        params.put("studentName", studentName);
        params.put("studentPhone", studentPhone);
        params.put("organizationName", organizationName);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=");
        sb.append(type == null ? "" : type);
        sb.append("&begin_date=");
        sb.append(this.queryBeginDate == null ? "" : this.queryBeginDate);
        sb.append("&end_date=");
        sb.append(this.queryEndDate == null ? "" : this.queryEndDate);
        sb.append("&student_id=");
        sb.append(this.studentId == null ? "" : this.studentId);
        sb.append("&student_name=");
        sb.append(this.studentName == null ? "" : this.studentName);
        sb.append("&student_phone=");
        sb.append(this.studentPhone == null ? "" : this.studentPhone);
        sb.append("&organization_name=");
        sb.append(this.organizationName == null ? "" : this.organizationName);
        return sb.toString();
    }

}
