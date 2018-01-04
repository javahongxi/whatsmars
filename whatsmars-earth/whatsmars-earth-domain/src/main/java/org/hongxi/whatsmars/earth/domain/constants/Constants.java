package org.hongxi.whatsmars.earth.domain.constants;


public class Constants {


    public static final String HTTP_ENCRYPT_KEY ="yc2JffcREheFQlYFIAY5f9sY7uflgBTo";

    public static final int COOKIE_MAX_AGE = 24 * 60 * 60;//7 days

    public static final String TEACHER_DEFAULT_PASSWORD = "xuehaodai123_";

    public static final String VALIDATE_CODE_COOKIE_KEY = "_validate_code_";

    //mongodb中存储admin的操作日志
    public static final String MONGO_ADMIN_OPERATION_COLLECTION = "xhd-admin-operation";

    //mongodb中存储organization的操作日志
    public static final String MONGO_ORGANIZATION_OPERATION_COLLECTION = "xhd-organization-operation";

    //mongodb中存储
    public static final String MONGO_EMAIL_SEND_ERROR_COLLECTION = "xhd-email-send-error";
    //发短信记录表
    public static final String MONGO_SMS_SEND_OPERATION_COLLECTION = "xhd-sms-send-operation";
    //学生账户操作记录
    public static final String MONGO_STUDENT_ACCOUNT_FLOW = "student-account-flow";
    //投资人账户操作记录
    public static final String MONGO_LENDER_ACCOUNT_FLOW = "lender-account-flow";
    //机构账户操作记录
    public static final String MONGO_ORGANIZATION_ACCOUNT_FLOW = "organization-account-flow";
    //学好贷账户操作记录
    public static final String MONGO_ADMIN_ACCOUNT_FLOW = "admin-account-flow";



    public static final String PHONE_VALIDATE_CODE_COOKIE_KEY = "_phone_validate_code_";

    //发送短信  对学生用【阳光E学】签名  对投资人用【学好贷】签名
    public static final String SMS_SEND_URL = "http://117.79.237.29:8061/mdsmssend.ashx";
    public static final String SMS_SEND_SN = "SDK-SKY-010-02698";
    public static final String SMS_SEND_PASSWORD = "85A1D86BFCA3A598C1F9C357070994EB";


    public static final String[] DATE_PATTERN = new String[]{"yyyy-MM-dd"};

    public static final String[] DATE_TIME_PATTERN = new String[]{"yyyy-MM-dd HH:mm:ss"};

    public static final String DATE_PATTERN_CHINESE = "yyyy年MM月dd日";

    public static final String[] FILE_FORMAT = {".pdf", ".doc", ".docx", ".xls", ".xlsx", ".jpg", ".jpeg", ".png", ".html", ".htm"};

    public static final String[] IMAGE_FORMAT = {".jpg", ".jpeg", ".png"};



    //系统中所用的文件或图片（从管理后台上传，/upload/upload.jhtml）
    //机构资料-数据查询授权书
    public static final String AUTHORIZATION_FILE = "http://stat.xuehaodai.com/ds/5fu9t2l2t3wziqlgctrmjfoedirf7x6k.pdf";
    //邮件头部 LOGO
    public static final String EMAIL_LOGO = "http://stat.xuehaodai.com/ds/dkh4eeiwz1wqbiszgvrdwmsmsl5gkv9m.png";
    //协议文档
    public static final String LOAN_PROTOCOL_FILE = "http://stat.xuehaodai.com/ds/2ou1puzlibhlwbgj4wqshq8ulh3hwrmb.pdf";


}
