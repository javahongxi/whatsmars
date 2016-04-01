package com.whatsmars.mars001.common.util;

import javax.mail.PasswordAuthentication;

/**
 * Created by Administrator on 2015/4/10 0010.
 */
public class Authenticator extends javax.mail.Authenticator {
    String userName=null;
    String password=null;

    public Authenticator(){
    }
    public Authenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}
