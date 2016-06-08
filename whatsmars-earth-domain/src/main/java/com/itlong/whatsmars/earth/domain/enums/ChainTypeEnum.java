package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by shenhongxi on 15/4/28.
 */
public enum ChainTypeEnum {

    CHAIN_STORE(1,"直营"),
    FRANCHISEE(2,"加盟"),
    OTHER(3,"其他");

    public int code;

    public String meaning;

    ChainTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static ChainTypeEnum value(int code) {
        for(ChainTypeEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }
}
