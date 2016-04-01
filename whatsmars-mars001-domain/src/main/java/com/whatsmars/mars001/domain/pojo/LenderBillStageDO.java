package com.whatsmars.mars001.domain.pojo;

import java.util.Date;

/**
 * Created by jenny on 6/2/15.
 */
public class LenderBillStageDO {
    private Integer id;
    private int studentId;
    private int biddingId;
    private int biddingItemId;
    private int lenderId;
    private String phone;
    private int stage;
    private int totalStage;
    private Double amount;
    private Double interest;        //投资的总收益
    private Double principal;
    private String studentName;
    private String courseName;
    private Date deadline;
    private Date repaymentDate;

    private int overdueDays;
    private Double overdueFee;

    //当期应还总金额
    private Double totalAmount;
    //当期最迟还款时间
    private String repayDeadline;

    private int status;
    private Date created;
    private Date modified;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    private Integer version;

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

    public int getLenderId() {
        return lenderId;
    }

    public void setLenderId(int lenderId) {
        this.lenderId = lenderId;
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

    public int getBiddingItemId() {
        return biddingItemId;
    }

    public void setBiddingItemId(int biddingItemId) {
        this.biddingItemId = biddingItemId;
    }
}
