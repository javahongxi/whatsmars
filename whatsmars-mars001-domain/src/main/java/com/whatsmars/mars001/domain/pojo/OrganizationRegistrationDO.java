package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shenhongxi on 2015/4/10.
 * 机构注册信息
 */
public class OrganizationRegistrationDO implements Serializable {

    private Integer id;

    private Integer organizationId;

    private Integer foundType; // 机构开办形式

    private String unionName; // 公司/民办非企业单位名称

    private String address; // 注册地址

    private String licenseOffice; // 登记机关/办学许可机关

    private String foundDate; // 成立时间

    private String inspectionDate; // 最近一次年检时间

    private String telephone; // 机构固定电话

    private String chain; // 是否全国连锁

    private String website; // 网址

    private String description; // 大事记

    private String licenseImage; // 营业执照副本/办学许可证

    private String codeImage; // 组织机构代码证

    private String taxImage; // 税务登记证

    //private String financeImage; // 财务报表

    //private String auditImage; // 审计报告

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

    public Integer getFoundType() {
        return foundType;
    }

    public void setFoundType(Integer foundType) {
        this.foundType = foundType;
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

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenseOffice() {
        return licenseOffice;
    }

    public void setLicenseOffice(String licenseOffice) {
        this.licenseOffice = licenseOffice;
    }

    public String getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(String foundDate) {
        this.foundDate = foundDate;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicenseImage() {
        return licenseImage;
    }

    public void setLicenseImage(String licenseImage) {
        this.licenseImage = licenseImage;
    }

    public String getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(String codeImage) {
        this.codeImage = codeImage;
    }

    public String getTaxImage() {
        return taxImage;
    }

    public void setTaxImage(String taxImage) {
        this.taxImage = taxImage;
    }

    /*public String getFinanceImage() {
        return financeImage;
    }

    public void setFinanceImage(String financeImage) {
        this.financeImage = financeImage;
    }

    public String getAuditImage() {
        return auditImage;
    }

    public void setAuditImage(String auditImage) {
        this.auditImage = auditImage;
    }*/
}
