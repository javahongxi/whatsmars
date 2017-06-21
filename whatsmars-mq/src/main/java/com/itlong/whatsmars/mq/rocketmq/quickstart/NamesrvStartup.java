package com.itlong.whatsmars.mq.rocketmq.quickstart;

/**
 * Created by shenhongxi on 2017/6/21.
 */
public class NamesrvStartup {

    public static void main(String[] args) {
        args = new String[] {"-c", "D:\\github\\whatsmars\\whatsmars-mq\\src\\main\\resources\\conf.properties"};
        org.apache.rocketmq.namesrv.NamesrvStartup.main(args);
    }
}
