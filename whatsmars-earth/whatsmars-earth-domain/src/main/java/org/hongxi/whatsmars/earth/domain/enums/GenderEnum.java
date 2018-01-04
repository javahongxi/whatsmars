package org.hongxi.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum GenderEnum {

    MALE(0, "男"),

    FEMALE(1, "女");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    GenderEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }
    public static GenderEnum value(int code){
        for(GenderEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
