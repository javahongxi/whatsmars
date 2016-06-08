package com.itlong.whatsmars.earth.domain.query;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by jenny on 6/2/15.
 */
public class LenderBillStageQuery extends BaseQuery {
    private Integer biddingId;

    private Integer status;

    private Integer studentId;

    private Integer lenderId;

    private String phone;

    private String studentName;

    private Integer type;

    //提交时间-开始
    private String beginDate;

    private String endDate;

    private String queryEndDate;

    private String queryBeginDate;

    private String keyWord;


    public Integer getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Integer biddingId) {
        this.biddingId = biddingId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getLenderId() {
        return lenderId;
    }

    public void setLenderId(Integer lenderId) {
        this.lenderId = lenderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getQueryBeginDate() {
        return queryBeginDate;
    }

    public String getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }



    public void setQueryBeginDate(String queryBeginDate) {
        this.queryBeginDate = queryBeginDate;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("biddingId",biddingId);
        params.put("status",status);
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        params.put("type",type);
        params.put("keyWord",keyWord);
        params.put("lenderId",lenderId);
        params.put("lenderPhone",phone);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("&biddingId=");
        sb.append(this.biddingId == null ? "" : this.biddingId);
        sb.append("&status=");
        sb.append(this.status == null ? "" : this.status);
        sb.append("&studentId=");
        sb.append(this.studentId == null ? "" : this.studentId);
        sb.append("&lenderId=");
        sb.append(this.lenderId == null ? "" : this.lenderId);

        sb.append("type=");
        sb.append(type == null ? "" : type);

        if (StringUtils.isNotBlank(beginDate)) {
            sb.append("&begin_date=").append(beginDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            sb.append("&end_date=").append(endDate);
        }
        if (StringUtils.isNotBlank(keyWord)) {
            sb.append("&key_word=").append(keyWord);
        }
        if (StringUtils.isNotBlank(phone)) {
            sb.append("&phone=").append(phone);
        }
        return sb.toString();
    }
}
