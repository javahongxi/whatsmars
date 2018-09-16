package org.hongxi.whatsmars.javase.socket;

import java.io.*;
import java.net.*;

public class TCPClientModel {
	
	public static void main(String[] args) {
		new TCPClientModel().connect();
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
				if (dos != null) dos.close();
				if (s != null) s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
