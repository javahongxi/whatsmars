package com.itlong.whatsmars.earth.domain.query;

import java.util.Map;

/**
 * Created by duanxiangchao on 2015/4/15.
 */
public class TeacherQuery extends BaseQuery{

    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<String, Object> build() {
        Map<String,Object> params = super.build();
        params.put("name",this.name);
        params.put("phone",phone);
        return params;
    }

    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name=");
        sb.append(this.name == null ? "" : this.name);
        sb.append("&phone=");
        sb.append(this.phone == null ? "" : this.phone);
        return sb.toString();
    }

}
