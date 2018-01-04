package org.hongxi.whatsmars.base.rmi;

import java.rmi.Naming;

/**
 * Created by shenhongxi on 2016/4/18.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        UserService userService = (UserService) Naming.lookup("rmi://127.0.0.1:8899/userService");
        User user = new User();
        user.setName("Mars");
        user.setAge(27);
        String hi = userService.hi(user);
        System.out.println(hi);
    }
}
