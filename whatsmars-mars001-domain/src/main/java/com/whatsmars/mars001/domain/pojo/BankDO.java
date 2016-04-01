package com.whatsmars.mars001.domain.pojo;

import java.io.Serializable;

/**
 * Created by shenhongxi on 15/4/24.
 */
public class BankDO implements Serializable {
    private Integer id;

    private String name;

    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
