package org.hongxi.whatsmars.base.nio;// $Id$

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class UseCharsets
{
  static public void main( String args[] ) throws Exception {
    String inputFile = "samplein.txt";
    String outputFile = "sampleout.txt";

    RandomAccessFile inf = new RandomAccessFile( inputFile, "r" );
    RandomAccessFile outf = new RandomAccessFile( outputFile, "rw" );
    long inputLength = new File( inputFile ).length();

    FileChannel inc = inf.getChannel();
    FileChannel outc = outf.getChannel();

    MappedByteBuffer inputData =
      inc.map( FileChannel.MapMode.READ_ONLY, 0, inputLength );

    Charset latin1 = Charset.forName( "ISO-8859-1" );
    CharsetDecoder decoder = latin1.newDecoder();
    CharsetEncoder encoder = latin1.newEncoder();

    CharBuffer cb = decoder.decode( inputData );

    // Process char data here

    ByteBuffer outputData = encoder.encode( cb );

    outc.write( outputData );

    inf.close();
    outf.close();
  }
}
