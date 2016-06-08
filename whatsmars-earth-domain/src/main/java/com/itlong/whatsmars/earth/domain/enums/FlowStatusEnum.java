package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by cuichengrui on 2015/6/2.
 */
public enum FlowStatusEnum {

    FAILURE(0, "失败"),
    SUCCESS(1, "成功"),
    ONGOING(2, "处理中");

    public int code;

    public String meaning;

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    FlowStatusEnum(int code,String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static FlowStatusEnum value(int code) {
        for(FlowStatusEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }
}
