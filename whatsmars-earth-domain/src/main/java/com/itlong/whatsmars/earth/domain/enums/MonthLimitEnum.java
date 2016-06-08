package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum MonthLimitEnum {


    SIX(6, "六个月"),

    TWELVE(12, "十二个月"),

    EIGHTEEN(18, "十八个月"),

    TWENTY_FOUR(24, "二十四个月");


    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    MonthLimitEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static MonthLimitEnum value(int code){
        for(MonthLimitEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
