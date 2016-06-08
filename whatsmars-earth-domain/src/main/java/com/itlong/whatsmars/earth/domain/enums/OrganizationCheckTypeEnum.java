package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by shenhongxi on 15/4/28.
 * 机构复检类型（频率）
 */
public enum OrganizationCheckTypeEnum {
    QUARTER(1,"每季"),
    MONTH(2,"每月");

    public int code;
    public String meaning;//意义

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    OrganizationCheckTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static OrganizationCheckTypeEnum value(int code) {
        for(OrganizationCheckTypeEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }

}
