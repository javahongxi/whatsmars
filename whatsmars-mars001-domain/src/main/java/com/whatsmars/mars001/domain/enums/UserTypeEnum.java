package com.whatsmars.mars001.domain.enums;

/**
 * Created by liuguanqing on 15/4/3.
 */
public enum UserTypeEnum {
    INVESTOR(1,"投资人"),
    STUDENT(2,"借款人"),
    ORGANIZATION(3,"机构"),
    TEACHER(4,"教师"),
    ADMIN(5,"管理员");

    public int code;
    public String meaning;//意义

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    UserTypeEnum(int code,String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static UserTypeEnum value(int code) {
        for(UserTypeEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }


}
