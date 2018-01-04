package org.hongxi.whatsmars.earth.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shenhongxi on 2016/6/8.
 */
public class Organization implements Serializable {

    private Integer id;

    private String code;

    private int level;

    private String auditNote;

    private String email;//机构登陆邮箱

    private String name;//机构名称

    private String password;

    private int status;//参见OrganizationStatusEnum；

    private String bailPercentage; // 保证金比例

    private Integer checkType; // 复检类型（频率）

    private Date created;

    private Date modified;

    private Date submitResourceDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuditNote() {
        return auditNote;
    }

    public void setAuditNote(String auditNote) {
        this.auditNote = auditNote;
    }

    public Date getSubmitResourceDate() {
        return submitResourceDate;
    }

    public void setSubmitResourceDate(Date submitResourceDate) {
        this.submitResourceDate = submitResourceDate;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBailPercentage() {
        return bailPercentage;
    }

    public void setBailPercentage(String bailPercentage) {
        this.bailPercentage = bailPercentage;
    }
}
