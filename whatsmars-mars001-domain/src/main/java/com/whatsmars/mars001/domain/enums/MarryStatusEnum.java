package com.whatsmars.mars001.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum MarryStatusEnum {

    UNMARRIED(0, "未婚"),

    MARRIED(1, "已婚"),

    DIVORCED(3, "离异");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    MarryStatusEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static MarryStatusEnum value(int code){
        for(MarryStatusEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
