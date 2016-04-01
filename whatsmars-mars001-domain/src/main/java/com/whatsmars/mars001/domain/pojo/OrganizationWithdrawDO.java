package com.whatsmars.mars001.domain.pojo;

import java.util.Date;

/**
 * Created by gongzaifei on 15/6/2.
 */
public class OrganizationWithdrawDO {

    private Integer id;

    private Integer organizationId; //机构ID

    private Double  amount; // 机构提现金额

    private Integer status; // 机构提现状态

    private Date  created; // 机构提现申请时间

    private Date  modified; //学好贷审批时间

    private String organizationName; //机构名称

    private Double fee;//提现手续费

    private String organizationCode; //机构code

    private Double balance; //机构账户提现前余额

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
