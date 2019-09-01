package org.hongxi.java.util.concurrent;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

/**
 * @author shenhongxi 2019/09/01
 */
public class NonReentrantLockTest {

    static NonReentrantLock lock = new NonReentrantLock();
    static Condition notFull = lock.newCondition();
    static Condition notEmpty = lock.newCondition();

    static Queue<String> queue = new LinkedBlockingQueue<>();
    static int queueSize = 10;

    public static void main(String[] args) {
        Thread producer = new Thread(() -> {
            lock.lock();
            try {
                while (queue.size() == queueSize) {
                    notEmpty.await();
                }
                queue.add("ele");
                notFull.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread consumer = new Thread(() -> {
            lock.lock();
            try {
                while (queue.size() == 0) {
                    notFull.await();
                }
                System.out.println(queue.poll());
                notEmpty.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        producer.start();
        consumer.start();
    }
}
