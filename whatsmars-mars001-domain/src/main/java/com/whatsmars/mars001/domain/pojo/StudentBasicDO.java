package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jenny on 4/22/15.
 *
 */
public class StudentBasicDO implements Serializable {

    private Integer id;

    private int studentId;
    /**
     * 性别
     *
     * @see com.xhd.domain.enums.GenderEnum
     */
    private int gender;
    /**
     * 婚姻情况
     *
     * @see com.xhd.domain.enums.MarryStatusEnum
     */
    private int marryStatus;
    /**
     * 孩子数量
     *
     * @see com.xhd.domain.enums.ChildNumberEnum
     */

    private int children;

    private String email;

    private String qq;

    private String residence; // 居所情况

    private String address; //现居住地址

    private Integer censusAddressType;//户籍地址类型

    private Integer censusType;//户籍类型

    private String cardFontImage;//身份证--正面照片

    private String cardBackImage;//身份证--背面照片

    private String censusIndexImage;//户口本首页图片

    private String censusSelfImage;//户口本本人页图片

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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(int marryStatus) {
        this.marryStatus = marryStatus;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public Integer getCensusType() {
        return censusType;
    }

    public void setCensusType(Integer censusType) {
        this.censusType = censusType;
    }

    public String getCardFontImage() {
        return cardFontImage;
    }

    public void setCardFontImage(String cardFontImage) {
        this.cardFontImage = cardFontImage;
    }

    public String getCardBackImage() {
        return cardBackImage;
    }

    public void setCardBackImage(String cardBackImage) {
        this.cardBackImage = cardBackImage;
    }

    public String getCensusIndexImage() {
        return censusIndexImage;
    }

    public void setCensusIndexImage(String censusIndexImage) {
        this.censusIndexImage = censusIndexImage;
    }

    public String getCensusSelfImage() {
        return censusSelfImage;
    }

    public void setCensusSelfImage(String censusSelfImage) {
        this.censusSelfImage = censusSelfImage;
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

    public Integer getCensusAddressType() {
        return censusAddressType;
    }

    public void setCensusAddressType(Integer censusAddressType) {
        this.censusAddressType = censusAddressType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
