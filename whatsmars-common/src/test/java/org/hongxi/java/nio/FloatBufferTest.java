package org.hongxi.java.nio;

import java.nio.FloatBuffer;

/**
 * FloatBuffer
 * @author shenhongxi 2019/8/15
 */
public class FloatBufferTest {

    public static void main(String[] args) {
        FloatBuffer buffer = FloatBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            float f = (float) Math.sin((((float) i) / 10) * (2 * Math.PI));
            buffer.put(f);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            float f = buffer.get();
            System.out.println(f);
        }
    }
}
