package com.whatsmars.mars001.domain.enums;

/**
 * Created by chenguang on 2015/4/29 0029.
 * mongodb日志操作的类型
 */
public enum OperationsLogTypeEnum {

    AUDIT_ORGANIZATION(1, "机构资质审核"),
    UPDATE_BAIL_PERCENT(2, "保证金比例修改"),
    AUDIT_COURSE(3, "课程审核"),
    STUDENT_ORGANIZATION_AUDIT(4,"机构审核学生信息"),
    BIDDING_ADMIN_AUDIT(5,"管理员发标审核"),
    BIDDING_FULL_ADMIN_AUDIT(6,"管理员满标审核"),
    STUDENT_ACCOUNT_FLOW(7,"学生账户流水"),
    LENDER_ACCOUNT_FLOW(8,"投资人账户流水"),
    ORGANIZATION_ACCOUNT_FLOW(9,"机构账户流水"),
    ADMIN_ACCOUNT_FLOW(10,"学好贷平台流水");

    public int code;

    public String meaning;

    public int getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    OperationsLogTypeEnum(int code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    public static OperationsLogTypeEnum value(int code) {
        for(OperationsLogTypeEnum e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }
}
