package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by duanxiangchao on 2015/6/11.
 */
public enum AdvanceTypeEnum {

    ORGANIZATION(0, "机构垫付"),
    PLATFORM(1, "学好贷平台垫付");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    AdvanceTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static AdvanceTypeEnum value(int code){
        for(AdvanceTypeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
