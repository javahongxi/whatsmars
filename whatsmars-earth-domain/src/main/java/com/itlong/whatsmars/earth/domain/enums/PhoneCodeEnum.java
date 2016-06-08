package com.itlong.whatsmars.earth.domain.enums;

/**
 * Created by duanxiangchao on 2015/5/9.
 */
public enum PhoneCodeEnum {

    REGISTER(1,"注册手机验证码"),
    FIND_PASSWORD(2,"找回密码"),
    UPDATE_PHONE(3,"更改手机号");


    public int code;

    public String meaning;

    public int getCode() {
        return code;

    }

    public String getMeaning() {
        return meaning;
    }

    PhoneCodeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static PhoneCodeEnum value(int code){
        for(PhoneCodeEnum e:values()){
            if(e.code==code){
                return e;
            }
        }
        return null;
    }

}
