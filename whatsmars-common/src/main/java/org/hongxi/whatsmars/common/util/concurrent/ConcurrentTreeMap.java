package org.hongxi.whatsmars.common.util.concurrent;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * thread safe
 */
public class ConcurrentTreeMap<K, V> {
    private final ReentrantLock lock;
    private TreeMap<K, V> tree;
    private RoundQueue<K> roundQueue;

    public ConcurrentTreeMap(int capacity, Comparator<? super K> comparator) {
        tree = new TreeMap<K, V>(comparator);
        roundQueue = new RoundQueue<K>(capacity);
        lock = new ReentrantLock(true);
    }

    public Map.Entry<K, V> pollFirstEntry() {
        lock.lock();
        try {
            return tree.pollFirstEntry();
        } finally {
            lock.unlock();
        }
    }

    public V putIfAbsent(K key, V value) {
        lock.lock();
        try {
            if (roundQueue.put(key)) {
                V oldValue = tree.get(key);
                if (null == oldValue) {
                    tree.put(key, value);
                    oldValue = value;
                }
                return oldValue;
            } else {
                V oldValue = tree.get(key);
                return oldValue;
            }
        } finally {
            lock.unlock();
        }
    }

}
