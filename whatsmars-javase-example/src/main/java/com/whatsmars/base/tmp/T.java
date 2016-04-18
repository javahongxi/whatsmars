package com.whatsmars.base.tmp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhongxi on 2015/11/9.
 */
public class T {
    public static void main(String[] args) throws Exception {
        String path = "E:/btdata/bt_cumulation.txt";
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = null;
        br.readLine();

        PrintWriter pw = new PrintWriter("E:/btdata/bt_cumulation2.txt");
        int i = 0;
        while ((line = br.readLine()) != null) {
            //System.out.println("'" + line + "',");
            String[] ss = line.split(",");
            String pin = ss[1];
            pw.println(pin);
            if (i++ % 10 == 0) {
                pw.flush();
                System.out.println(i);
            }
        }

        br.close();
        pw.close();
    }
}
