package org.hongxi.java.util.collections;

/**
 * @author shenhongxi 2019/8/12
 */
public class ListReverseTest {

    public static void main(String[] args) {
        SimpleList<Integer> list = new SimpleList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        list.reverse();
        System.out.println(list);
    }
}
