package org.hongxi.java.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel
 * @author shenhongxi 2019/8/15
 */
public class CopyFileTest {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java CopyFile infile outfile");
            System.exit(1);
        }

        String infile = args[0];
        String outfile = args[1];

        FileInputStream fin = new FileInputStream(infile);
        FileOutputStream fout = new FileOutputStream(outfile);

        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();

        /**
         * faster:
         * ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
         */
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            buffer.clear();
            int r = fcin.read(buffer);
            if (r == -1) {
                break;
            }
            buffer.flip();
            fcout.write(buffer);
        }
    }
}
