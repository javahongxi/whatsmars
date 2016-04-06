package com.ixhong.base.thread.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ThreadTest1 {
	private List<String> list = new ArrayList<String>();

	public List<String> getList() {
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		ThreadTest1 lines = new ThreadTest1();
		PrintThread t = new PrintThread(lines);
		t.start();
		lines.go();
	}

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
}

class PrintThread extends Thread {
	private ThreadTest1 lines;

	public PrintThread(ThreadTest1 lines) {
		this.lines = lines;
	}

	public void run() {
		while (true) {
			try {
				System.out.println(lines.getList());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}
	}
}
