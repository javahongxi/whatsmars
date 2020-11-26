package org.hongxi.java.nio;

import java.nio.ByteBuffer;

/**
 * ByteBuffer
 * @author shenhongxi 2019/8/15
 */
public class BufferTest {

    public static void main(String[] args) {
        /**
         * byte[] array = new byte[1024];
         * ByteBuffer buffer = ByteBuffer.wrap(array);
         */
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put((byte) 'a');
        buffer.put((byte) 'b');
        buffer.put((byte) 'c');

        buffer.flip();

        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());

        buffer.clear();

        buffer.putInt(30);
        buffer.putLong(7000000000000L);
        buffer.putDouble(Math.PI);

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
    }
}
