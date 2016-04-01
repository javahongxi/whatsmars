package com.whatsmars.mars001.domain.enums;

/**
 * Created by shenhongxi on 15/4/12.
 * 机构资产类型
 */
public enum OrganizationPropertyTypeEnum {
    PRIVATE(1,"自有"),
    RENT(2,"租用");

    public int code;
    public String meaning;//意义

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    OrganizationPropertyTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static OrganizationPropertyTypeEnum value(int code) {
        for(OrganizationPropertyTypeEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }

}
