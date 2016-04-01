package com.whatsmars.mars001.domain.misc;


import com.whatsmars.mars001.domain.pojo.UserDO;

/**
 * Author: qing
 * Date: 14-10-29
 * 保存当前登陆用户
 */
public class LoginContext {

    private UserDO user;


    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

}
