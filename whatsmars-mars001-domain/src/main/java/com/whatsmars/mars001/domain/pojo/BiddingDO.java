package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cuichengrui on 2015/4/24
 */
public class BiddingDO implements Serializable {

    //主键
    private Integer id;

    //学生id
    private int studentId;

    //学生姓名
    private String studentName;

    //学生手机号
    private String studentPhone;

    //机构id
    private int organizationId;

    //机构名称
    private String organizationName;

    //咨询老师
    private String teacher;

    //课程id
    private int courseId;

    //课程名称
    private String courseName;

    //已付金额
    private Double paid;

    //借款金额
    private Double required;

    //已募集到的金额
    private Double obtained;

    //已还的金额
    private Double repaid;

    private Double process;//进度

    //借款期限
    private int monthLimit;

    private double rate;//利率

    //基本服务费
    private Double feeRate ;
    //宽恕期外服务费
    private Double monthRate ;
    //宽恕期内服务费
    private Double graceRate;

    //仅还利息期限
    private int gracePeriod;

    //预计结课日期
    private String courseEndDate;

    //学生已签署的借款合同上传
    private String contractImage;

    private Double interest;//预期总收益

    //投标的状态
    private int status;

    private Double bailPercentage;

    private int effective;//是否有效

    private Date invalidDate;//流标日期

    private Date repayBeginDate;//还款开始时间

    private Date created;

    private Date modified;

    private int version;

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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
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

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
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

    public Double getRequired() {
        return required;
    }

    public void setRequired(Double required) {
        this.required = required;
    }

    public Double getObtained() {
        return obtained;
    }

    public void setObtained(Double obtained) {
        this.obtained = obtained;
    }

    public Double getRepaid() {
        return repaid;
    }

    public void setRepaid(Double repaid) {
        this.repaid = repaid;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getEffective() {
        return effective;
    }

    public void setEffective(int effective) {
        this.effective = effective;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getContractImage() {
        return contractImage;
    }

    public void setContractImage(String contractImage) {
        this.contractImage = contractImage;
    }

    public Double getProcess() {
        return process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public Date getRepayBeginDate() {
        return repayBeginDate;
    }

    public void setRepayBeginDate(Date repayBeginDate) {
        this.repayBeginDate = repayBeginDate;
    }

    public Double getBailPercentage() {
        return bailPercentage;
    }

    public void setBailPercentage(Double bailPercentage) {
        this.bailPercentage = bailPercentage;
    }

    public Double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(Double feeRate) {
        this.feeRate = feeRate;
    }

    public Double getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(Double monthRate) {
        this.monthRate = monthRate;
    }

    public Double getGraceRate() {
        return graceRate;
    }

    public void setGraceRate(Double graceRate) {
        this.graceRate = graceRate;
    }
}
