package com.whatsmars.mars001.domain.pojo;

import java.util.Date;

/**
 * Created by duanxiangchao on 2015/5/13.
 */
public class OrganizationAccountFlowDO {

    private Integer id;
    private int  organizationId;
    private int studentId;
    private String studentName;
    private String studentPhone;
    private int type;
    private Double amount;              //操作金额
    private Double balanceBefore;       //操作前余额
    private Double balanceAfter;        //操作后余额
    private Double bailBefore;          //操作前保证金
    private Double bailAfter;           //操作后保证金
    private Double withdraw;            //提现金额
    private Double fee;                 //手续费(提现)
    private Double frozenBefore;        //操作前冻结金额(提现)
    private Double frozenAfter;         //操作后冻结金额(提现)
    private int status;                 //交易状态(提现)
    private String orderId;             //订单号(提现)
    private String batchNo;             //打款批次号(提现)
    private Integer withdrawId;         //提现记录 ID
    private int  biddingId;
    private Date created;
    private Date modified;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(int biddingId) {
        this.biddingId = biddingId;
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

    public Double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Double getBailAfter() {
        return bailAfter;
    }

    public void setBailAfter(Double bailAfter) {
        this.bailAfter = bailAfter;
    }

    public Double getBailBefore() {
        return bailBefore;
    }

    public void setBailBefore(Double bailBefore) {
        this.bailBefore = bailBefore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
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

    public Double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Double withdraw) {
        this.withdraw = withdraw;
    }

    public Double getFee() {
        return fee;
    }

    public Double getFrozenBefore() {
        return frozenBefore;
    }

    public void setFrozenBefore(Double frozenBefore) {
        this.frozenBefore = frozenBefore;
    }

    public Double getFrozenAfter() {
        return frozenAfter;
    }

    public void setFrozenAfter(Double frozenAfter) {
        this.frozenAfter = frozenAfter;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Integer withdrawId) {
        this.withdrawId = withdrawId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
