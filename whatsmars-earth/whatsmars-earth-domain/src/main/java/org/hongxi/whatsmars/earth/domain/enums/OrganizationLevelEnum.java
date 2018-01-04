package org.hongxi.whatsmars.earth.domain.enums;

/**
 * Created by shenhongxi on 15/6/29.
 * 机构类别
 */
public enum OrganizationLevelEnum {
    A(1,"A类"),
    B(2,"B类");

    public int code;
    public String meaning;//意义

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    OrganizationLevelEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static OrganizationLevelEnum value(int code) {
        for(OrganizationLevelEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }

}
