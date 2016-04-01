package com.whatsmars.mars001.domain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jenny on 6/11/15.
 */
public enum LenderFlowTypeEnum {

    RECHARGE(0, "充值"),

    WITHDRAW(1, "提现"),

    //理财人收到还款
    RECEIVE(3, "收到还款"),

    //理财人投资冻结
    FREEZE(4, "投标冻结"),

    //理财人流标退款
    REFUND(5,"流标退款"),

    //理财人满标时投资成功，扣除投资人冻结余额
    BID_SUCCESS(6, "投资成功"),

    //投资人提现，学好贷收取提现手续费
    WITHDRAW_FEE(11, "提现手续费");

    public Integer code;

    public String meaning;

    public static Map<String,String> map = new HashMap<String, String>();

    static {
        map.put((RECHARGE.code.toString()),RECHARGE.meaning);
        map.put(WITHDRAW.code.toString(),WITHDRAW.meaning);

        map.put(RECEIVE.code.toString(),RECEIVE.meaning);
        map.put(FREEZE.code.toString(),FREEZE.meaning);

        map.put(REFUND.code.toString(),REFUND.meaning);
        map.put(BID_SUCCESS.code.toString(),BID_SUCCESS.meaning);

        map.put(WITHDRAW_FEE.code.toString(),WITHDRAW_FEE.meaning);

    }

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    LenderFlowTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static LenderFlowTypeEnum value(int code){
        for(LenderFlowTypeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }

    public static Map getMap(){
        return map;
    }

}
