package com.whatsmars.mars001.domain.pojo;

import java.util.Date;

/**
 * Created by chenguang on 2015/5/14 0014.
 */
public class LenderAccountDO {

    private Integer id;

    private Integer lenderId;

    private Double balance; //余额

    private Double balanceFrozen; //冻结金额

    private Double principal;//持有本金

    private Double totalInterest;//累计收益和预期收益总和

    private Double currentInterest;//累计收益

    private Double pendingEarn; //待收益

    private String bankCardId; //银行卡号

    private String bankName;

    private String dealPassword;

    private int version;

    private Date created;

    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLenderId() {
        return lenderId;
    }

    public void setLenderId(Integer lenderId) {
        this.lenderId = lenderId;
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

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(Double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public Double getCurrentInterest() {
        return currentInterest;
    }

    public void setCurrentInterest(Double currentInterest) {
        this.currentInterest = currentInterest;
    }

    public Double getPendingEarn() {
        return pendingEarn;
    }

    public void setPendingEarn(Double pendingEarn) {
        this.pendingEarn = pendingEarn;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBalanceFrozen() {
        return balanceFrozen;
    }

    public void setBalanceFrozen(Double balanceFrozen) {
        this.balanceFrozen = balanceFrozen;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDealPassword() {
        return dealPassword;
    }

    public void setDealPassword(String dealPassword) {
        this.dealPassword = dealPassword;
    }

}
