package org.hongxi.java.util.collections;

import org.junit.Test;

/**
 * @author shenhongxi 2019/8/13
 */
public class CollectionsTest {

    @Test
    public void testArrayList() {
        ArrayList arrList = new ArrayList();
        arrList.add(1);
        arrList.add(2);
        arrList.add(3);
        arrList.add(null);
        System.out.println(arrList);
    }

    @Test
    public void testLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        list.reverse();
        System.out.println(list);
    }

    @Test
    public void testHashMap() {
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

    @Test
    public void testStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
