package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shenhongxi on 2015/4/10.
 * 机构主要人员信息
 */
public class OrganizationMemberDO implements Serializable {

    private Integer id;

    private Integer organizationId;

    private String corporation; // 法人代表姓名

    private String cardId; // 法人代表身份证号

    private String phone; // 法人代表手机号

    private String description; // 法人代表学历及工作经历简述

    private String cardFrontImage; // 法人代表身份证正面

    private String cardBackImage; // 法人代表身份证反面

    private String contact; // 联系人姓名

    private String contactPhone; // 联系人手机号

    private String contactTitle; // 联系人职务

    private String contactCardId; // 联系人身份证号

    private String shareholders; // 股东列表,JSON

    private String managers; // 高层管理员列表，JSON

    private Date created;

    private Date modified;

    private String creditImage;//法人征信报告

    private String authorizationImage;//查询授权书扫描件

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

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardFrontImage() {
        return cardFrontImage;
    }

    public void setCardFrontImage(String cardFrontImage) {
        this.cardFrontImage = cardFrontImage;
    }

    public String getCardBackImage() {
        return cardBackImage;
    }

    public void setCardBackImage(String cardBackImage) {
        this.cardBackImage = cardBackImage;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getContactCardId() {
        return contactCardId;
    }

    public void setContactCardId(String contactCardId) {
        this.contactCardId = contactCardId;
    }

    public String getShareholders() {
        return shareholders;
    }

    public void setShareholders(String shareholders) {
        this.shareholders = shareholders;
    }

    public String getManagers() {
        return managers;
    }

    public void setManagers(String managers) {
        this.managers = managers;
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

    public String getCreditImage() {
        return this.creditImage;
    }

    public void setCreditImage(String creditImage) {
        this.creditImage = creditImage;
    }

    public String getAuthorizationImage() {
        return authorizationImage;
    }

    public void setAuthorizationImage(String authorizationImage) {
        this.authorizationImage = authorizationImage;
    }
}
