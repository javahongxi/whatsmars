package com.whatsmars.mars001.domain.enums;

/**
 * Created by shenhongxi on 15/4/12.
 */
public enum OrganizationFoundTypeEnum {
    COMPANY(1,"公司"),
    PRIVATE_SCHOOL(2,"民办学校"),
    PUBLIC_SCHOOL(3,"公办学校");

    public int code;
    public String meaning;//意义

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    OrganizationFoundTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static OrganizationFoundTypeEnum value(int code) {
        for(OrganizationFoundTypeEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }


}
