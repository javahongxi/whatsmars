package com.itlong.whatsmars.base.socket;

import java.io.*;
import java.net.*;

public class UDPClientModel {
	public static void main(String[] args) {
		try {
			long n = 1000L;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeLong(n);
			//byte[] buf = new String("Hello").getBytes();
			byte[] buf = baos.toByteArray();
			DatagramPacket dp = new DatagramPacket(buf, buf.length, 
					new InetSocketAddress("localhost", 5678));
			DatagramSocket ds = new DatagramSocket(9999);
			ds.send(dp);
			ds.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
