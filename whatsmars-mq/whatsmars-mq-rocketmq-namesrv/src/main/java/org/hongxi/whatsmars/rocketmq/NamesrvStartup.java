package org.hongxi.whatsmars.rocketmq;

/**
 * Created by shenhongxi on 2017/6/21.
 */
public class NamesrvStartup {

    public static void main(String[] args) {
        String classpath = NamesrvStartup.class.getResource("/").getPath();
        args = new String[] {"-c", classpath + "namesrv.properties"};
        org.apache.rocketmq.namesrv.NamesrvStartup.main(args);
    }
}
