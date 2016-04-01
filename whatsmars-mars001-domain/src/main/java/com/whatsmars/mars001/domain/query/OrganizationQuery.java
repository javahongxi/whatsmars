package com.whatsmars.mars001.domain.query;

import java.util.Map;

/**
 * Created by shenhongxi on 15/4/16.
 */
public class OrganizationQuery extends BaseQuery {

    private String name;

    private String code;

    private String email;

    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("name",this.name);
        params.put("code", this.code);
        params.put("email", this.email);
        params.put("status",status);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name=");
        sb.append(this.name == null ? "" : this.name);
        sb.append("&code=");
        sb.append(this.code == null ? "" : this.code);
        sb.append("&email=");
        sb.append(this.email == null ? "" : this.email);
        sb.append("&status=");
        sb.append(this.status == null ? "" : this.status);
        return sb.toString();
    }

}
