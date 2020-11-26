package org.hongxi.java.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by javahongxi on 2018/1/2.
 */
public class TCPServer {
	boolean started;
	ServerSocket ss;
	
	public static void main(String[] args) {
		new TCPServer().start();
	}
	
	private void start() {
		try {
			ss = new ServerSocket(5555);
			started = true;
			System.out.println("Server started");
		} catch (BindException e) {
			e.printStackTrace();
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
		
		@Override
		public void run() {
			try {
				System.out.println(dis.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (dis != null) {
						dis.close();
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
}
