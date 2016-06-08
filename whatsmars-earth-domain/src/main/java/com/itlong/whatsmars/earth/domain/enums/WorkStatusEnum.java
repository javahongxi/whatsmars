package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 4/26/15.
 */
public enum WorkStatusEnum {

    IN_PREPARE(1, "离开学校准备工作"),

    AT_SCHOOL(2, "在校学习"),

    IN_WORK(3, "固定在职工作");

    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    WorkStatusEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static WorkStatusEnum value(int code){
        for(WorkStatusEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
