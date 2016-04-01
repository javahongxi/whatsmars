package com.whatsmars.mars001.domain.enums;

/**
 * Created by gongzaifei on 15/6/3.
 */
public enum OrganizationWithdrawStatusEnum {

        AUDITING(0,"审核中"),
        AUDITING_SUCCESS(1,"审核通过"),
        AUDITING_FAILED(2,"审核不通过"),
        SUCCESS(3,"打款成功"),
        FAILED(4,"打款失败");

        public int code;

        public String meaning;

        OrganizationWithdrawStatusEnum(int code,String meaning){
            this.code = code;
            this.meaning = meaning;
        }
        public static OrganizationWithdrawStatusEnum value(int code){
            for(OrganizationWithdrawStatusEnum statusEnum : values()){
                if(statusEnum.code == code){
                    return statusEnum;
                }
            }
            return null;
        }
        public int getCode(){
                return code;
        }

        public String getMeaning(){
            return meaning;
        }
}
