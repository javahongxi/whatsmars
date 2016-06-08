package com.itlong.whatsmars.earth.domain.query;

import java.util.Map;

/**
 * Created by chenguang on 2015/5/25 0025.
 */
public class InvestmentQuery extends BaseQuery {

    private String name;

    private String phone;

    private String realName;

    public Map<String, Object> build() {
        Map<String, Object> params = super.build();
        params.put("name", name);
        params.put("phone", phone);
        params.put("realName", realName);
        return params;
    }

    public String queryString() {
        StringBuffer sb = new StringBuffer();
        sb.append("&name=");
        sb.append(name == null ? "" : name);
        sb.append("&phone=");
        sb.append(phone == null ? "" : phone);
        sb.append("&real_name=");
        sb.append(realName == null ? "" : realName);
        return sb.toString();
    }

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
