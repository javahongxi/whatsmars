package com.whatsmars.mars001.domain.enums;

/**
 * Created by cuichengrui on 15/4/24.
 */
public enum BiddingStatusEnum {

    ORGANIZATION_AUDITING(10,"机构待审核","机构待审核"),
    ORGANIZATION_AUDITED_FAILURE(1,"机构审核拒绝通过","借款失败"),

    ADMIN_AUDITING(20,"学好贷发标待审","学好贷审核中"),
    ADMIN_AUDITED_FAILURE(2,"学好贷发标审拒绝通过","借款失败"),

    ONGOING(30,"投标中","学好贷审核中"),
    FAILURE(3,"流标","借款失败"),

    FULL_ADMIN_AUDITING(40,"学好贷满标待审","等待签约放款"),
    FULL_ADMIN_AUDITED_FAILURE(4,"学好贷满标审拒绝通过","借款失败"),

    STAGING(50,"还款中","借款成功，还款中"),
    PAID_OFF(60,"还款成功","已还清");

    public int code;

    public String meaning;

    public String meaningOfUser;

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getMeaningOfUser() {
        return meaningOfUser;
    }

    BiddingStatusEnum(int code, String meaning,String meaningOfUser) {
        this.code = code;
        this.meaning = meaning;
        this.meaningOfUser = meaningOfUser;
    }

    public static BiddingStatusEnum value(int code) {
        for(BiddingStatusEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }
}
