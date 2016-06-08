package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by liuguanqing on 15/4/7.
 * 教师状态
 */
public enum TeacherStatusEnum {

    INIT(0,"初始状态"),
    SUCCESS(1,"正常状态");

    public int code;
    public String meaning;

    TeacherStatusEnum(int code,String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static TeacherStatusEnum value(int code) {
        for(TeacherStatusEnum e : values()) {
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
