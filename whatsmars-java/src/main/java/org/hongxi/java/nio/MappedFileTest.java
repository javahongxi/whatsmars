package org.hongxi.java.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer
 * Created by javahongxi on 2018/1/2.
 */
public class MappedFileTest {
    static private final int start = 0;
    static private final int size = 1024;

    static public void main(String args[]) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("/Users/javahongxi/mappedfile.txt", "rw");
        FileChannel channel = raf.getChannel();

        MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_WRITE, start, size);
        mbb.put(0, (byte) 97);
        mbb.put(1023, (byte) 122);

        raf.close();
    }
}
