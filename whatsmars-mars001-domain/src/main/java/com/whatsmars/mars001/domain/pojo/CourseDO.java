package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cuichengrui on 15/4/10.
 * 机构课程信息
 */
public class CourseDO implements Serializable {

    private Integer id;

    private String name;//课程名称

    private String organizationName;//机构名称

    private int organizationId;//机构ID

    private float price;//课程价格

    private int totalMonths;//课程时长

    private String description;//课程描述

    private int status;//课程审核状态，参见CourseStatusEnum

    private String auditNote;//课程审核备注

    private int effective;//课程是否有效

    private int monthLimit;//借款期限

    private int gracePeriod;//宽恕期

    private double feeRate;//月基础服务费率

    private double graceRate;//宽恕期内月服务费率

    private double monthRate;//宽恕期外月服务费率

    private Date created;//课程创建时间

    private Date modified;//课程修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTotalMonths() {
        return totalMonths;
    }

    public void setTotalMonths(int totalMonths) {
        this.totalMonths = totalMonths;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAuditNote() {
        return auditNote;
    }

    public void setAuditNote(String auditNote) {
        this.auditNote = auditNote;
    }

    public int getEffective() {
        return effective;
    }

    public void setEffective(int effective) {
        this.effective = effective;
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

    public int getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(int monthLimit) {
        this.monthLimit = monthLimit;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

    public double getGraceRate() {
        return graceRate;
    }

    public void setGraceRate(double graceRate) {
        this.graceRate = graceRate;
    }

    public double getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(double monthRate) {
        this.monthRate = monthRate;
    }
}
