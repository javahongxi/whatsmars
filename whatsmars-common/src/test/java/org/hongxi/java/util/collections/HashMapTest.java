package org.hongxi.java.util.collections;

/**
 * @author shenhongxi 2019/8/13
 */
public class HashMapTest {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "first");
        map.put(2, "second");
        System.out.println(map.get(1));
        System.out.println(map.size());
        System.out.println(map.containsKey(1));
        System.out.println(map.containsValue("first"));
        System.out.println(map.remove(1));
        System.out.println(map.remove(2));
        System.out.println(map.isEmpty());
    }
}
