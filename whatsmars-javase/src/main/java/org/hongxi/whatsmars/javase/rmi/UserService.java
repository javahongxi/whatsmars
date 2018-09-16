package org.hongxi.whatsmars.javase.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by shenhongxi on 2016/4/18.
 */
public interface UserService extends Remote {

    public String hi(User user) throws RemoteException;
}
