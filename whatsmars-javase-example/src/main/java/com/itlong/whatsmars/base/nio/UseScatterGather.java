package com.itlong.whatsmars.base.nio;// $Id$

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class UseScatterGather
{
  static private final int firstHeaderLength = 2;
  static private final int secondHeaderLength = 4;
  static private final int bodyLength = 6;

  static public void main( String args[] ) throws Exception {
    if (args.length!=1) {
      System.err.println( "Usage: java UseScatterGather port" );
      System.exit( 1 );
    }

    int port = Integer.parseInt( args[0] );

    ServerSocketChannel ssc = ServerSocketChannel.open();
    InetSocketAddress address = new InetSocketAddress( port );
    ssc.socket().bind( address );

    int messageLength =
      firstHeaderLength + secondHeaderLength + bodyLength;

    ByteBuffer buffers[] = new ByteBuffer[3];
    buffers[0] = ByteBuffer.allocate( firstHeaderLength );
    buffers[1] = ByteBuffer.allocate( secondHeaderLength );
    buffers[2] = ByteBuffer.allocate( bodyLength );

    SocketChannel sc = ssc.accept();

    while (true) {

      // Scatter-read into buffers
      int bytesRead = 0;
      while (bytesRead < messageLength) {
        long r = sc.read( buffers );
        bytesRead += r;

        System.out.println( "r "+r );
        for (int i=0; i<buffers.length; ++i) {
          ByteBuffer bb = buffers[i];
          System.out.println( "b "+i+" "+bb.position()+" "+bb.limit() );
        }
      }

      // Process message here

      // Flip buffers
      for (int i=0; i<buffers.length; ++i) {
        ByteBuffer bb = buffers[i];
        bb.flip();
      }

      // Scatter-write back out
      long bytesWritten = 0;
      while (bytesWritten<messageLength) {
        long r = sc.write( buffers );
        bytesWritten += r;
      }

      // Clear buffers
      for (int i=0; i<buffers.length; ++i) {
        ByteBuffer bb = buffers[i];
        bb.clear();
      }

      System.out.println( bytesRead+" "+bytesWritten+" "+messageLength );
    }
  }
}
