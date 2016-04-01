package com.whatsmars.mars001.domain.pojo;

import java.util.Date;

/**
 * Created by duanxiangchao on 2015/5/13.
 */
public class StudentBillStageDO {

    private Integer id;
    private int studentId;
    private int biddingId;
    private int organizationId;
    private String organizationName;
    private String phone;
    private int stage;
    private int totalStage;
    private Double amount;
    private Double interest;
    private Double principal;
    private String studentName;
    private String courseName;
    private Double fee;
    private Date deadline;
    private Date repaymentDate;
    private int overdueDays;
    private Double overdueFee;
    private String quartzDate;  //逾期任务调度日期标识
    private int advanceType;   //垫付类型   0 机构垫付   1 学好贷垫付

    //当期应还总金额
    private Double totalAmount;
    //当期最迟还款时间
    private String repayDeadline;
    //是否逾期  0未逾期   1逾期
    private int isOverdue;

    private int version;

    private int status;
    private Date created;
    private Date modified;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(int biddingId) {
        this.biddingId = biddingId;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getTotalStage() {
        return totalStage;
    }

    public void setTotalStage(int totalStage) {
        this.totalStage = totalStage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    public Double getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(Double overdueFee) {
        this.overdueFee = overdueFee;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRepayDeadline() {
        return repayDeadline;
    }

    public void setRepayDeadline(String repayDeadline) {
        this.repayDeadline = repayDeadline;
    }

    public int getAdvanceType() {
        return advanceType;
    }

    public void setAdvanceType(int advanceType) {
        this.advanceType = advanceType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(int isOverdue) {
        this.isOverdue = isOverdue;
    }

    public String getQuartzDate() {
        return quartzDate;
    }

    public void setQuartzDate(String quartzDate) {
        this.quartzDate = quartzDate;
    }
}

