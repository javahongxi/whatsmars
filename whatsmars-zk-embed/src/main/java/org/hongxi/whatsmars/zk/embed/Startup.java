package org.hongxi.whatsmars.zk.embed;

/**
 * Created by shenhongxi on 2020/9/17.
 */
public class Startup {

    public static void main(String[] args) {
        EmbedZookeeperServer.start(2181);
    }
}
