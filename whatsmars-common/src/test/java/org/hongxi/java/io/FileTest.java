package org.hongxi.java.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by shenhongxi on 2018/10/25.
 */
public class FileTest {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("/Users/javahongxi/Documents/white_list.txt"));
        PrintWriter pw = new PrintWriter("/Users/javahongxi/Documents/white_list02.txt");
        String line = null;
        while ((line = br.readLine()) != null) {
            pw.println(line);
        }
        pw.flush();
        pw.close();
        br.close();
    }
}
