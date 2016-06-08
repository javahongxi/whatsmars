package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/22/15.
 */
public enum DegreeEnum {

    JUNIOR_HIGH_SCHOOL(0,"初中（含）及以下"),

    HIGH_SCHOOL(1, "高中"),

    TECHNICAL_SCHOOL(2, "技校"),

    POLYTECHNIC_SCHOOL(3, "中专"),

    VOCATIONAL_SCHOOL(4,"职高"),

    JUNIOR_COLLEGE(5,"大专"),

    UNDERGRADUATE(6,"本科"),

    POSTGRADUATE(7,"研究生（含）及以上");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    DegreeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static DegreeEnum value(int code){
        for(DegreeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
