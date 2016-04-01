package com.whatsmars.mars001.domain.query;

import java.util.Map;

/**
 * Created by liuguanqing on 15/4/15.
 */
public class CourseQuery extends BaseQuery {

    //课程名称
    private String name;

    //机构名称
    private String organizationName;

    //课程审核状态
    private Integer status;

   //课程是否有效
    private Integer effective;


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("name",this.name);
        params.put("organizationName",this.organizationName);
        params.put("status",this.status);
        params.put("effective",this.effective);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name=");
        sb.append(this.name == null ? "" : this.name);
        sb.append("&status=");
        sb.append(this.status == null ? "" : this.status);
        sb.append("&organization_name=");
        sb.append(this.organizationName == null ? "" : this.organizationName);
        sb.append("&effective=");
        sb.append(this.effective == null ? "" : this.effective);
        return sb.toString();
    }

}
