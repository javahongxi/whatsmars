package org.hongxi.java.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by javahongxi on 2018/1/2.
 */
public class TCPClient {
	
	public static void main(String[] args) {
		new TCPClient().connect();
	}
	
	public void connect() {
		@SuppressWarnings("unused")
		boolean started = false;
		Socket s = null;
		DataOutputStream dos = null;
		try {
			s = new Socket("127.0.0.1", 5555);
			dos = new DataOutputStream(s.getOutputStream());
			started = true;
			System.out.println("Yeah, I connected");
			Thread.sleep(3000);
			dos.writeUTF("Happy");
			dos.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dos != null) {
					dos.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
