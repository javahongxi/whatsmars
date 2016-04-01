package com.whatsmars.mars001.domain.enums;

/**
 * Created by jenny on 6/26/15.
 */
public enum BailDegreeNum {

    JUNIOR_LOWER(4,"大专以下"),

    JUNIOR(5,"大专"),

    UNDERGRADUATE(6,"本科及以上");

    public int code;

    public String meaning;

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    BailDegreeNum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static BailDegreeNum value(int code){
        for(BailDegreeNum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
