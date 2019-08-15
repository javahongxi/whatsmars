package org.hongxi.java.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * FileLock
 * @author shenhongxi 2019/8/15
 */
public class FileLockTest {
    private static final int start = 10;
    private static final int end = 20;

    public static void main(String[] args) throws Exception {
        // Get file channel
        RandomAccessFile raf = new RandomAccessFile("usefilelocks.txt", "rw");
        FileChannel fc = raf.getChannel();

        // Get lock
        System.out.println("trying to get lock");
        FileLock lock = fc.lock(start, end, false);
        System.out.println("got lock!");

        // Pause
        System.out.println("pausing");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
        }

        // Release lock
        System.out.println("going to release lock");
        lock.release();
        System.out.println("released lock");

        raf.close();
    }
}
