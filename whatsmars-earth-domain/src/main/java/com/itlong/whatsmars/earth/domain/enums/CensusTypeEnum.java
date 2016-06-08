package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum CensusTypeEnum {

    VILLAGE(0, "农村"),

    CITY(1, "城市");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    CensusTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static CensusTypeEnum value(int code){
        for(CensusTypeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
