package org.hongxi.whatsmars.base.socket;

import java.io.*;
import java.net.*;

public class TCPServerModel {
	boolean started;
	ServerSocket ss;
	
	public static void main(String[] args) {
		new TCPServerModel().start();
	}
	
	private void start() {
		try {
			ss = new ServerSocket(5555);
			started = true;
			System.out.println("Server started");
		} catch (BindException e) {//���쳣
			System.out.println("�˿�ʹ����....\n" + "��ص���س����������з�������");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			
			while(started) {
				Socket s = ss.accept();
				Client c = new Client(s);
                System.out.println("a client connected!");
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ss != null) ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class Client extends Thread {
		private Socket s;
		private DataInputStream dis;
		
		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			try {
				System.out.println(dis.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (dis != null) dis.close();
					if (s != null) s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
