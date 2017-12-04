package com.whatsmars.earth.domain.misc;


import com.whatsmars.earth.domain.pojo.User;

/**
 * Author: qing
 * Date: 14-10-29
 * 保存当前登陆用户
 */
public class LoginContext {

    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
