package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/26/15.
 */
public enum GraduateStatusEnum {

    OWNER(1, "在读"),

    RENT(2, "毕业"),

    PARENTS(3, "肄业");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    GraduateStatusEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static GraduateStatusEnum value(int code){
        for(GraduateStatusEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
