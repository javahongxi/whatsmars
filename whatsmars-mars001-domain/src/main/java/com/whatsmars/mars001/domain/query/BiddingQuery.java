package com.whatsmars.mars001.domain.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by cuichengrui on 15/4/22.
 */
public class BiddingQuery extends BaseQuery {

    //借款人姓名
    private String studentName;

    //借款人手机号
    private String studentPhone;

    //机构名称
    private String organizationName;

    //课程名称
    private String courseName;

    //查询状态
    private Integer queryStatus;

    private List<Integer> status;

    //期限从。。。到。。。
    private Integer fromMonthLimit;

    private Integer toMonthLimit;

    //利率从。。。到。。。
    private Double fromRate;

    private Double toRate;

    //进度从。。。到。。。
    private Double fromProcess;

    private Double toProcess;

    // 按投标进度排序
    private String processOrder;

    //按投资金额排序
    private String amountOrder;

    //按月期限排序
    private String monthLimitOrder;

    //按排序顺序
    private String order;


    //提交时间-开始
    private Date beginDate;

    private String queryBeginDate;

    //提交时间-结束
    private Date endDate;

    private String rateOrder;

    private String queryEndDate;

    private Integer organizationId;

    private Integer effective;

    public Integer getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(Integer queryStatus) {
        this.queryStatus = queryStatus;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
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

    public Integer getFromMonthLimit() {
        return fromMonthLimit;
    }

    public void setFromMonthLimit(Integer fromMonthLimit) {
        this.fromMonthLimit = fromMonthLimit;
    }

    public Integer getToMonthLimit() {
        return toMonthLimit;
    }

    public void setToMonthLimit(Integer toMonthLimit) {
        this.toMonthLimit = toMonthLimit;
    }

    public Double getFromRate() {
        return fromRate;
    }

    public void setFromRate(Double fromRate) {
        this.fromRate = fromRate;
    }

    public Double getToRate() {
        return toRate;
    }

    public void setToRate(Double toRate) {
        this.toRate = toRate;
    }

    public Double getFromProcess() {
        return fromProcess;
    }

    public void setFromProcess(Double fromProcess) {
        this.fromProcess = fromProcess;
    }

    public Double getToProcess() {
        return toProcess;
    }

    public void setToProcess(Double toProcess) {
        this.toProcess = toProcess;
    }

    public String getProcessOrder() {
        return processOrder;
    }

    public void setProcessOrder(String processOrder) {
        this.processOrder = processOrder;
    }

    public String getAmountOrder() {
        return amountOrder;
    }

    public void setAmountOrder(String amountOrder) {
        this.amountOrder = amountOrder;
    }

    public String getMonthLimitOrder() {
        return monthLimitOrder;
    }

    public void setMonthLimitOrder(String monthLimitOrder) {
        this.monthLimitOrder = monthLimitOrder;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public String getRateOrder() {
        return rateOrder;
    }

    public void setRateOrder(String rateOrder) {
        this.rateOrder = rateOrder;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("studentName",this.studentName);
        params.put("studentPhone",this.studentPhone);
        params.put("courseName",this.courseName);
        params.put("organizationName",this.organizationName);
        params.put("status",this.status);
        params.put("beginDate",this.beginDate);
        params.put("endDate",this.endDate);
        params.put("organizationId",this.organizationId);

        params.put("fromMonthLimit",this.fromMonthLimit);
        params.put("toMonthLimit",this.toMonthLimit);

        params.put("fromRate",this.fromRate);
        params.put("toRate",this.toRate);

        params.put("fromProcess",this.fromProcess);
        params.put("toProcess",this.toProcess);

        params.put("amountOrder",this.amountOrder);
        params.put("order",this.order);
        params.put("monthLimitOrder",this.monthLimitOrder);
        params.put("processOrder",this.processOrder);
        params.put("rateOrder",this.rateOrder);




        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("student_name=");
        sb.append(this.studentName == null ? "" : this.studentName);
        sb.append("&student_phone=");
        sb.append(this.studentPhone == null ? "" : this.studentPhone);
        sb.append("&organization_name=");
        sb.append(this.organizationName == null ? "" : this.organizationName);
        sb.append("&course_name=");
        sb.append(this.courseName == null ? "" : this.courseName);
        sb.append("&status=");
        sb.append(this.queryStatus == null ? "" : this.queryStatus);
        sb.append("&begin_date=");
        sb.append(this.queryBeginDate == null ? "" : this.queryBeginDate);
        sb.append("&end_date=");
        sb.append(this.queryEndDate == null ? "" : this.queryEndDate);


        sb.append("&monthLimit =");
        sb.append(this.queryEndDate == null ? "" : this.queryEndDate);


        return sb.toString();
    }

}
