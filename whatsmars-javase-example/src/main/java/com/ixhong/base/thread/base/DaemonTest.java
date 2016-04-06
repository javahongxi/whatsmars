package com.ixhong.base.thread.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DaemonTest {
	public static void main(String[] args) throws Exception {
		DaemonTest q = new DaemonTest();
		q.action("d:/temp/test.txt");
	}
	
	private void action(String filename) throws IOException {
		List<String> lines = new ArrayList<String>(); 
		System.out.print("your name: ");
		Scanner in = new Scanner(System.in);
		String name = in.nextLine();
		
		readQuestion(lines, filename, name);
		
		TimerThread t = new TimerThread();
		t.setDaemon(true);
		t.start();
		
		solveQuestion(lines, name);
	}

	private void readQuestion(List<String> lines, String filename, String name) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(filename)), "gbk"));
		String line = null;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();

		System.out.println("��ʼ����...");
	}
	
	private void solveQuestion(List<String> lines, String name) throws IOException {
		int total = lines.size() / 3;
		int ok = 0;
		
		for (int i = 1; i <= total; i++) {
			System.out.println(i + "). " + lines.get(3 * (i - 1)));
			String optionLine = lines.get(3 * (i - 1) + 1);
			String[] options = optionLine.split(" ");
			for (int j = 0; j < options.length; j++)
				System.out.println("    " + j + "." + options[j]);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String input = in.readLine();
			if ("exit".equalsIgnoreCase(input))
				break;

			int answer = Integer.parseInt(input.trim());
			int key = Integer.parseInt(lines.get(3 * (i - 1) + 2));
			if (answer == key) {
				ok++;
			}
		}
		
		System.out.println(name + ", ������" + ok + "����Ŀ");
	}
	
	private class TimerThread extends Thread {
		public void run() {
			while (true) {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {}
				System.out.println("�������ʱ���ѵ���");
			}
		}
	}
}


