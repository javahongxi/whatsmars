package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum ChildNumberEnum {

    ZERO(0,"无"),

    ONE(1, "一个"),

    TWO(2, "两个"),

    MORE(3, "三个以上");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    ChildNumberEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static ChildNumberEnum value(int code){
        for(ChildNumberEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
