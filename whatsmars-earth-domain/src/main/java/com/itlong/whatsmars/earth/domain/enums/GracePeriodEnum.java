package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum GracePeriodEnum {

    THREE(3,"三个月"),

    SIX(6, "六个月"),

    NINE(9,"九个月"),

    TWELVE(12, "十二个月");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    GracePeriodEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static GracePeriodEnum value(int code){
        for(GracePeriodEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
