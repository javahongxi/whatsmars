package org.hongxi.java.util.collections;

/**
 * @author shenhongxi 2019/8/12
 */
public class ArrListTest {

    public static void main(String[] args) {
        SimpleArrList arrList = new SimpleArrList();
        arrList.add(1);
        arrList.add(2);
        arrList.add(3);
        arrList.add(null);
        System.out.println(arrList);
    }
}
