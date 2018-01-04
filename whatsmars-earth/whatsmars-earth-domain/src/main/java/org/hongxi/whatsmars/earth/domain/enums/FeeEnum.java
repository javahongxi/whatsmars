package org.hongxi.whatsmars.earth.domain.enums;

/**
 * Created by chenguang on 2015/4/24 0024.
 * 收费
 */
public enum FeeEnum {

    BELOW_2W(0.5d, "%/每月","借款管理费,两万元以下"),//借款管理费,两万以下
    ABOVE_2W(0.24d, "%/每月","借款管理费,两万元以上"),//借款管理费，两万以上
    RECHARGE(0.2d, "%/充值金额","充值手续费"),
    WITHDRAWALS(2, "元/每笔","提现手续费");

    public double code;

    public String meaning;

    public String name;

    public double getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    public String getName() {
        return name;
    }

    FeeEnum(double code, String meaning, String name) {
        this.code = code;
        this.meaning = meaning;
        this.name = name;
    }

    public static FeeEnum value(double code){
        for(FeeEnum e:values()){
            if(e.code == code){
                return e;
            }
        }
        return null;
    }
}
