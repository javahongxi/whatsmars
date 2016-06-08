package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by liuguanqing on 15/4/7.
 */
public enum OrganizationStatusEnum {

    EMAIL_VALIDATING(0,"邮箱验证中"),
    MATERIAL_NOT_SUBMITTED(10,"资料尚未提交"),
    MATERIAL_AUDITING(20,"资料审核中"),
    MATERIAL_AUDITED_SUCCESS(30,"资料审核通过"),
    MATERIAL_AUDITED_FAILED(40,"资料审核不通过");

    public int code;

    public String meaning;

    OrganizationStatusEnum(int code,String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static OrganizationStatusEnum value(int code) {
        for(OrganizationStatusEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }
}
