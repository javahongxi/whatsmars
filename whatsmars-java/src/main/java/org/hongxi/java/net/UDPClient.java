package org.hongxi.java.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by javahongxi on 2018/1/2.
 */
public class UDPClient {
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
