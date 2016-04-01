package com.whatsmars.mars001.domain.query;

import java.util.Map;

/**
 * Created by gongzaifei on 15/6/29.
 */
public class ArticleQuery extends  BaseQuery {

    private String beginDate;

    private String endDate;

    private String title;

    private Integer type;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<String, Object> build() {
        Map<String, Object> params = super.build();
        params.put("beginDate", beginDate);
        params.put("endDate", endDate);
        params.put("type", type);
        params.put("title", title);
        return params;
    }

    public String queryString() {
        StringBuffer sb = new StringBuffer();
        sb.append("begin_date=");
        sb.append(beginDate == null ? "" : this.beginDate);
        sb.append("&end_date=");
        sb.append(endDate == null ? "" : this.endDate);
        sb.append("&type=");
        sb.append(type == null ? "" : this.type);
        sb.append("&title=");
        sb.append(title == null ? "" : this.title);
        return sb.toString();
    }
}
