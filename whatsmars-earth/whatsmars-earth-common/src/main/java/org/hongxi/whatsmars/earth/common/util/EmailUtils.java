package org.hongxi.whatsmars.earth.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jenny on 4/13/15.
 * 根据邮箱获取域名，找到邮箱登陆页
 */
public class EmailUtils {

    private static final String MAIL_126 = "http://mail.126.com/";
    private static final String MAIL_163 = "http://mail.163.com/";
    private static final String MAIL_QQ = "https://mail.qq.com/cgi-bin/loginpage";
    private static final String MAIL_SINA = "http://mail.sina.com.cn/";
    private static final String MAIL_HOTMAIL = "http://hotmail.msn.com/";
    private static final String MAIL_GMAIL = "http://mail.google.com/";
    private static final String MAIL_YAHOO = "https://login.yahoo.com/config/mail?.src=ym&.intl=hk";
    private static final String MAIL_ALIYUN = "https://mail.aliyun.com/";
    private static final String MAIL_139 = "http://mail.10086.cn/";
    private static final String MAIL_189 = "http://webmail30.189.cn/w2/";
    private static final String MAIL_SOHU = "http://mail.sohu.com/";

    private static final Map <String ,String> EMAIL_MAP = new HashMap<String,String>();

    static {
        EMAIL_MAP.put("126.com", MAIL_126);
        EMAIL_MAP.put("163.com", MAIL_163);
        EMAIL_MAP.put("qq.com", MAIL_QQ);
        EMAIL_MAP.put("sina.cn", MAIL_SINA);
        EMAIL_MAP.put("sina.com", MAIL_SINA);
        EMAIL_MAP.put("hotmail.com", MAIL_HOTMAIL);
        EMAIL_MAP.put("gmail.com", MAIL_GMAIL);
        EMAIL_MAP.put("yahoo.com", MAIL_YAHOO);
        EMAIL_MAP.put("aliyun.com", MAIL_ALIYUN);
        EMAIL_MAP.put("139.com", MAIL_139);
        EMAIL_MAP.put("189.cn", MAIL_189);
        EMAIL_MAP.put("sohu.com", MAIL_SOHU);
    }

    public static String getEmailLoginPage(String email){
        int index = email.lastIndexOf("@");
        if(index == -1) {
            return null;
        }
        return EMAIL_MAP.get(email.substring(index + 1));
    }

}
