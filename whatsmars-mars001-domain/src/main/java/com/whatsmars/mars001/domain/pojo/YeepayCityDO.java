package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;

/**
 * Created by duanxiangchao on 2015/4/14.
 * 易宝支付接口  省市国标
 */
public class YeepayCityDO implements Serializable {

    private Integer id;  //主键
    private String code; //编码
    private String pid;  //父ID
    private String name; //备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
