package com.itlong.whatsmars.base.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Exchanger<String> exchanger = new Exchanger<String>();
        service.execute(new Runnable() {
            public void run() {
                try {

                    String data1 = "zxx";
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "正在把数据" + data1 + "换出去");
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "换回的数据为" + data2);
                } catch (Exception e) {

                }
            }
        });
        service.execute(new Runnable() {
            public void run() {
                try {

                    String data1 = "lhm";
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "正在把数据" + data1 + "换出去");
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "换回的数据为" + data2);
                } catch (Exception e) {

                }
            }
        });
    }
}
