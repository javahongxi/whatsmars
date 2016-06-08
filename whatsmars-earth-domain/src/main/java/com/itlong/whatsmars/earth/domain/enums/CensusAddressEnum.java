package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum CensusAddressEnum {

    LOCAL(1, "本地"),

    REMOTE(2, "外地");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    CensusAddressEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static CensusAddressEnum value(int code){
        for(CensusAddressEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
