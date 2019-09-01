package org.hongxi.java.util.concurrent;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author shenhongxi 2019/09/01
 * 验证迭代器的弱一致性
 */
public class CopyOnWriteArrayListTest {

    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        arrayList.add("hello");
        arrayList.add("alibaba");
        arrayList.add("welcome");
        arrayList.add("to");
        arrayList.add("hangzhou");

        Thread threadOne = new Thread(() -> {
            arrayList.set(1, "baba");
            arrayList.remove(2);
            arrayList.remove(3);
        });

        // 保证在threadOne启动前获取迭代器
        Iterator<String> itr = arrayList.iterator();

        threadOne.start();

        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}
