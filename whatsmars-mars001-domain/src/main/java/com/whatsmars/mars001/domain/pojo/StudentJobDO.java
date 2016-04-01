package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jenny on 4/23/15.
 *
 */
public class StudentJobDO implements Serializable {


    private Integer id;

    private int studentId;

    /**
     * 就业状态
     * @see com.xhd.domain.enums.EmploymentStatusEnum
     */
    private int status;

    private String company; //公司

    private String department;//部门

    private String title;//职务

    private String beginDate;//入职时间

    private String certifier;//证明人

    private String certifierTitle;//证明人职务

    private String certifierPhone;//证明人联系方式

    private String certificateImage;//证件照片

    private Date created;

    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getCertifier() {
        return certifier;
    }

    public void setCertifier(String certifier) {
        this.certifier = certifier;
    }

    public String getCertifierTitle() {
        return certifierTitle;
    }

    public void setCertifierTitle(String certifierTitle) {
        this.certifierTitle = certifierTitle;
    }

    public String getCertifierPhone() {
        return certifierPhone;
    }

    public void setCertifierPhone(String certifierPhone) {
        this.certifierPhone = certifierPhone;
    }

    public String getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(String certificateImage) {
        this.certificateImage = certificateImage;
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
}
