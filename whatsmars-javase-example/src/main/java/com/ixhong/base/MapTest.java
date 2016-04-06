package com.ixhong.base;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by shenhongxi on 15/7/28.
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = new LinkedHashMap<String, Object>();
        map = new WeakHashMap<String, Object>();
        Set<String> set = new LinkedHashSet<String>();

        set = new TreeSet();
        map = new TreeMap<String, Object>();
        Iterator<String> iter = set.iterator();
        List<String> list = new ArrayList<String>();
        iter = list.iterator();
        list = new LinkedList<String>();

        Random r = new Random();
        int a = r.nextInt();
        System.out.println(a);

        map = new ConcurrentHashMap<String, Object>();
    }

}
