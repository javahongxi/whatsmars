package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shenhongxi on 2015/4/10.
 * 机构银行账户信息
 */
public class OrganizationAccountDO implements Serializable {

    private Integer id;

    private Integer organizationId;

    private String bankName; // 银行名称

    private String address; // 开户地

    private String bankBranch; // 开户支行名称

    private String name; // 账户名称

    private String bailPercentage; // 保证金比例

    private Double lendingQuotas; // 放款额度

    private String accountNumber; // 账号

    private String licenseImage; // 银行开户许可证

    private Double balance; // 余额

    private Double balanceFrozen; //冻结金额

    private Double bail; // 保证金

    private int version;

    private Date created;

    private Date modified;

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getLicenseImage() {
        return licenseImage;
    }

    public void setLicenseImage(String licenseImage) {
        this.licenseImage = licenseImage;
    }

    public Double getLendingQuotas() {
        return lendingQuotas;
    }

    public void setLendingQuotas(Double lendingQuotas) {
        this.lendingQuotas = lendingQuotas;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getBail() {
        return bail;
    }

    public void setBail(Double bail) {
        this.bail = bail;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Double getBalanceFrozen() {
        return balanceFrozen;
    }

    public void setBalanceFrozen(Double balanceFrozen) {
        this.balanceFrozen = balanceFrozen;
    }

    public String getBailPercentage() {
        return bailPercentage;
    }

    public void setBailPercentage(String bailPercentage) {
        this.bailPercentage = bailPercentage;
    }
}
