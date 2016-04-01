package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jenny on 4/23/15.
 * 学生亲属
 *
 */
public class StudentRelativesDO implements Serializable {

    private Integer id;

    private int studentId;

    private String primary; //首要亲属

    private String minor;//次要亲属

    private String other;//其他

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

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
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
