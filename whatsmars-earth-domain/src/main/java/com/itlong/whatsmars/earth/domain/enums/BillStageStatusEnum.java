package com.itlong.whatsmars.earth.domain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duanxiangchao on 2015/5/18.
 */
public enum BillStageStatusEnum {

    NO_PAID(10,"等待还款"),//未出账单

    PENDING(1, "等待还款"),//已出账单

    PAID(20, "已还");

    public Integer code;

    public String meaning;

    public static Map<String,String> lenderMap = new HashMap<String, String>();

    static {
        lenderMap.put(NO_PAID.code.toString(),"待收");
        lenderMap.put(PENDING.code.toString(),"待收");
        lenderMap.put(PAID.code.toString(),"已收");
    }

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    BillStageStatusEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static BillStageStatusEnum value(int code){
        for(BillStageStatusEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }

    public static Map<String,String> getLenderMap(){
        return lenderMap;
    }

}
