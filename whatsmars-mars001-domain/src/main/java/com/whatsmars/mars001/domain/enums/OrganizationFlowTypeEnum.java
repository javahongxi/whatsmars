package com.whatsmars.mars001.domain.enums;

/**
 * Created by jenny on 6/11/15.
 */
public enum OrganizationFlowTypeEnum {

    RECHARGE(0, "充值"),

    WITHDRAW(1, "提现"),

    //满标审核通过钱转给机构
    FULL_BIDDING_TRANSFER(7 , "满标转账"),

    //满标审核通过扣除机构保证金
    BAIL(8, "扣除保证金"),

    //借款人逾期，机构垫付
    ORGANIZATION_ADVANCE_AMOUNT(12, "机构垫付本息"),

    //机构垫付后借款人还款
    ORGANIZATION_RECEIVE_AFTER_ADVANCE(15, "机构垫付后收款");

    public Integer code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    OrganizationFlowTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static OrganizationFlowTypeEnum value(int code){
        for(OrganizationFlowTypeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }
}
