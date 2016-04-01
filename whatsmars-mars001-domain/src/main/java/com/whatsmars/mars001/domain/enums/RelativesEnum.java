package com.whatsmars.mars001.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum RelativesEnum {

    FATHER(1, "父亲"),

    MOTHER(2, "母亲"),

    MATE(3,"配偶"),

    RELATIVES(4, "其他亲属");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    RelativesEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static RelativesEnum value(int code){
        for(RelativesEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
