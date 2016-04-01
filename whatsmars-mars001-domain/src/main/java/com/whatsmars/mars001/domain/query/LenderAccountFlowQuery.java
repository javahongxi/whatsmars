package com.whatsmars.mars001.domain.query;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by jenny on 5/15/15.
 */
public class LenderAccountFlowQuery extends BaseQuery{
    //提交时间-开始
    private String beginDate;

    private String queryBeginDate;

    private Integer lenderId;

    //交易类型
    private Integer type;

    //提交时间-结束
    private String endDate;

    private String queryEndDate;

    private String keyWord;

    private String lenderName;

    private String lenderRealName;

    private String lenderPhone;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getQueryBeginDate() {
        return queryBeginDate;
    }

    public void setQueryBeginDate(String queryBeginDate) {
        this.queryBeginDate = queryBeginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getQueryEndDate() {
        return queryEndDate;
    }

    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLenderId() {
        return lenderId;
    }

    public void setLenderId(Integer lenderId) {
        this.lenderId = lenderId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public String getLenderRealName() {
        return lenderRealName;
    }

    public void setLenderRealName(String lenderRealName) {
        this.lenderRealName = lenderRealName;
    }

    public String getLenderPhone() {
        return lenderPhone;
    }

    public void setLenderPhone(String lenderPhone) {
        this.lenderPhone = lenderPhone;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("beginDate",beginDate);
        params.put("endDate",endDate);
        params.put("type",type);
        params.put("keyWord",keyWord);
        params.put("lenderId",lenderId);
        params.put("lenderName",lenderName);
        params.put("lenderRealName",lenderRealName);
        params.put("lenderPhone",lenderPhone);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type=");
        sb.append(type == null ? "" : type);
        sb.append("&id=");
        sb.append(lenderId == null ? "" : lenderId);
        if(StringUtils.isNotBlank(beginDate)){
            sb.append("&begin_date=").append(beginDate);
        }
        if(StringUtils.isNotBlank(endDate)) {
            sb.append("&end_date=").append(endDate);
        }
        if(StringUtils.isNotBlank(keyWord)) {
            sb.append("&key_word=").append(keyWord);
        }
        if(StringUtils.isNotBlank(lenderName)){
            sb.append("&name=").append(lenderName);
        }
        if(StringUtils.isNotBlank(lenderRealName)){
            sb.append("&real_name=").append(lenderRealName);
        }
        if(StringUtils.isNotBlank(lenderPhone)){
            sb.append("&phone=").append(lenderPhone);
        }
        return sb.toString();
    }
}
