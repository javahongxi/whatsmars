package com.itlong.whatsmars.base.thread.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ThreadTest2 {
	private List<String> list = new ArrayList<String>();

	private void go() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		System.out.println("input: ");
		while ((line = br.readLine()) != null) {
			if ("exit".equalsIgnoreCase(line)) {
				break;
			}
			list.add(line);
			System.out.println("input: ");
		}
	}

	private class PrintThread extends Thread {
		public void run() {
			while (true) {
				try {
					System.out.println(list);
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private void start() {
		Thread t = new PrintThread();
		t.start();
	}

	public static void main(String[] args) throws IOException {
		ThreadTest2 lines3 = new ThreadTest2();
		lines3.start();
		lines3.go();
	}
}
