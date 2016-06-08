package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/22/15.
 * 就业状态
 */
public enum EmploymentStatusEnum {

    STUDYING(0, "在读"),

    WORKED(1, "就业"),

    OTHER(2, "其他");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    EmploymentStatusEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static EmploymentStatusEnum value(int code){
        for(EmploymentStatusEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
