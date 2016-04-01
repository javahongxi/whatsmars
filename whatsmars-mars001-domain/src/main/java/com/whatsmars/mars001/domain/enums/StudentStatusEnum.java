package com.whatsmars.mars001.domain.enums;

/**
 * Created by jenny on 24/4/7.
 */
public enum StudentStatusEnum {

    INIT(10,"资料尚未提交"),
    AUDITING(20,"资料审核中"),
    SUCCESS(30,"资料审核通过"),
    FAILURE(40,"资料审核不通过");

    public int code;

    public String meaning;

    StudentStatusEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static StudentStatusEnum value(int code) {
        for(StudentStatusEnum e : values()) {
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
