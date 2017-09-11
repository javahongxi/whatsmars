package com.itlong.whatsmars.mq.zeromq;

import org.zeromq.ZMQ;

public class Subscriber {

    public static void main(String args[]) {  
        for (int j = 0; j < 100; j++) {  
            new Thread(new Runnable(){
                public void run() {  
                    ZMQ.Context context = ZMQ.context(1);
                    ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
                    subscriber.connect("tcp://127.0.0.1:5555");
                    subscriber.subscribe("toutiao".getBytes());

                    try {
                        while (true) {
                            byte[] message = subscriber.recv();
                            System.out.println("receive : " + new String(message));
                        }
                    } finally {
                        subscriber.close();
                        context.term();
                    }
                }
            }).start();  
        }
    }  
}