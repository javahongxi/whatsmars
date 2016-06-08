package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by jenny on 6/11/15.
 */
public enum AdminFlowTypeEnum {

    RECHARGE(0, "充值"),

    WITHDRAW(1, "提现"),

    //借款人还款，学好贷收到服务费
    RECEIVE_FEE(9, "收到服务费"),

    //投资人充值，学好贷支付充值费用
    RECHARGE_FEE(10, "支付充值费用"),

    //借款人逾期且机构保证金不足，平台垫付本息
    ADMIN_ADVANCE_AMOUNT(13, "平台垫付本息"),

    //平台或机构垫付后借款人还款，平台收到逾期罚金
    RECEIVE_FINE(14, "收到逾期罚金"),

    //平台垫付后借款人还款
    RECEIVE_AFTER_ADVANCE(16, "平台垫付后收款");

    public Integer code;

    public String meaning;


    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    AdminFlowTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static AdminFlowTypeEnum value(int code){
        for(AdminFlowTypeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }

    }
