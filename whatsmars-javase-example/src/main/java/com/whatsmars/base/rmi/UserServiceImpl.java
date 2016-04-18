package com.whatsmars.base.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by shenhongxi on 2016/4/18.
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    public UserServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void hi(String name) throws RemoteException {
        System.out.println("Hi, " + name);
    }
}
