package com.whatsmars.base.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created by shenhongxi on 2016/4/18.
 */
public class Server {

    public static void main(String[] args) throws Exception {
        UserService userService = new UserServiceImpl();
        LocateRegistry.createRegistry(8899);
        Naming.rebind("rmi://127.0.0.1:8899/userService", userService);
    }
}
