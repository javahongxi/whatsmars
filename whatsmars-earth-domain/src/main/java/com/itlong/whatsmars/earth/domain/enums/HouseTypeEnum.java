package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/26/15.
 */
public enum HouseTypeEnum {

    OWNER(1, "自有住房"),

    PARENTS(2, "父母住房"),

    RENT(3, "租房"),

    OTHERS(4,"其他亲友住房");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    HouseTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static HouseTypeEnum value(int code){
        for(HouseTypeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
