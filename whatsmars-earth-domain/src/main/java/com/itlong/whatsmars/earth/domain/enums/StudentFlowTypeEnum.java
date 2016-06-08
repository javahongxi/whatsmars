package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 6/11/15.
 */
public enum StudentFlowTypeEnum {


    RECHARGE(0, "充值"),

    WITHDRAW(1, "提现"),

    //借款人还款
    REPAYMENT(2, "还款");



    public Integer code;

    public String meaning;


    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    StudentFlowTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static StudentFlowTypeEnum value(int code){
        for(StudentFlowTypeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
