package com.whatsmars.mars001.domain.pojo;

import java.util.Date;

/**
 * Created by jenny on 5/11/15.
 */
public class LenderDO {

    private Integer id;

    private String phone;

    private String password;

    private String name;      //用户名

    private String realName;   //真实姓名

    private String cardId;     //身份证

    private Integer effective;

    private Date loginTime;

    private Integer certified; //实名认证状态

    private String created;

    private String modified;

    private Double balance; //不参与持久化

    private Double balanceFrozen; //不参与持久化

    private Double principal; //不参与持久化

    private Integer biddingCount; //投资人持有项目数，不参与持久化

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Integer getCertified() {
        return certified;
    }

    public void setCertified(Integer certified) {
        this.certified = certified;
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

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Integer getBiddingCount() {
        return biddingCount;
    }

    public void setBiddingCount(Integer biddingCount) {
        this.biddingCount = biddingCount;
    }
}
