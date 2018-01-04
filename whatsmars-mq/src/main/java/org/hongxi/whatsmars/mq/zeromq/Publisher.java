package org.hongxi.whatsmars.mq.zeromq;

import org.zeromq.ZMQ;

public class Publisher {

    public static void main(String args[]) {
        ZMQ.Context context = ZMQ.context(1); // 创建包含一个I/O线程的context
        ZMQ.Socket publisher = context.socket(ZMQ.PUB);
        publisher.bind("tcp://*:5555");
 
        while (!Thread.currentThread ().isInterrupted()) {
            String message = "toutiao hello";
            publisher.send(message.getBytes());
            System.out.println("sent : " + message);
        }  
 
        publisher.close();  
        context.term();  
    }  
}