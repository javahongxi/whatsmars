package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by cuichengrui on 15/4/10.
 */
public enum CourseStatusEnum {

    COURSE_CHECKING(0,"待审核"),
    COURSE_CHECKED_SUCCESS(10,"已通过"),
    COURSE_CHECKED_FAILURE(20,"未通过"),
    COURSE_DISABLED(30,"已停用");

    public int code;

    public String meaning;

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    CourseStatusEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static CourseStatusEnum value(int code) {
        for(CourseStatusEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }
}
