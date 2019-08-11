package org.hongxi.java.util.concurrent;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author shenhongxi 2019/8/12
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        final Queue queue = new Queue();
        final Random r = new Random();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> queue.get()).start();
            new Thread(() -> queue.put(r.nextInt(10000))).start();
        }
    }

    static class Queue {
        //共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
        private Object data = null;
        ReadWriteLock rwl = new ReentrantReadWriteLock();

        public void get() {
            rwl.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()
                        + " be ready to read data!");
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println(Thread.currentThread().getName()
                        + "have read data :" + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwl.readLock().unlock();
            }
        }

        public void put(Object data) {
            rwl.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()
                        + " be ready to write data!");
                Thread.sleep((long) (Math.random() * 1000));
                this.data = data;
                System.out.println(Thread.currentThread().getName()
                        + " have write data: " + data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwl.writeLock().unlock();
            }
        }
    }
}
