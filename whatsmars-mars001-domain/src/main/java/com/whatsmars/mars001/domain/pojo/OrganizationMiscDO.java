package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shenhongxi on 2015/4/10.
 * 机构之学校信息
 */
public class OrganizationMiscDO implements Serializable {

    private Integer id;

    private Integer organizationId;

    private int totalStaffs; // 员工数量

    private int totalTeachers; // 专职教师数量

    private int lastYearStudents; // 上一年招生总数量

    private int thisYearStudents; // 本年度截至目前招生总数量

    private String address; // 教学地址

    private int acreage; // 场地总面积

    private int totalClassrooms; // 教室数量

    private int propertyType; // 场地所有权信息

    private String devices; // 主要教学设备

    private float devicesValue; // 主要教学设备投入金额

    private Date created;

    private Date modified;

    private String housePropertyImage;

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

    public int getTotalStaffs() {
        return totalStaffs;
    }

    public void setTotalStaffs(int totalStaffs) {
        this.totalStaffs = totalStaffs;
    }

    public int getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(int totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAcreage() {
        return acreage;
    }

    public void setAcreage(int acreage) {
        this.acreage = acreage;
    }

    public int getTotalClassrooms() {
        return totalClassrooms;
    }

    public void setTotalClassrooms(int totalClassrooms) {
        this.totalClassrooms = totalClassrooms;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public float getDevicesValue() {
        return devicesValue;
    }

    public void setDevicesValue(float devicesValue) {
        this.devicesValue = devicesValue;
    }

    public String getHousePropertyImage() {
        return this.housePropertyImage;
    }

    public void setHousePropertyImage(String housePropertyImage) {
        this.housePropertyImage = housePropertyImage;
    }

    public int getLastYearStudents() {
        return lastYearStudents;
    }

    public void setLastYearStudents(int lastYearStudents) {
        this.lastYearStudents = lastYearStudents;
    }

    public int getThisYearStudents() {
        return thisYearStudents;
    }

    public void setThisYearStudents(int thisYearStudents) {
        this.thisYearStudents = thisYearStudents;
    }
}
