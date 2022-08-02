package com.funny.study.java.container.cache;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存Map
 * @author fangli
 * @date 2018-12-20 10:13:18
 */
public class CacheMap<K, V> {
    private Map<K, V> cache;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public CacheMap(int capacity) {
        final int cap = capacity == 0 ? 16 : capacity;
        this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > cap;
            }
        };
    }

    /**
     * 从缓存获取value,和map.get用法一样
     * @param key key
     * @return value
     * @author lilun
     * @date 2018-12-20 10:13:18
     */
    public <K> V get(K key) {
        try {
            readWriteLock.readLock().lock();
            return this.cache.containsKey(key) ? this.cache.get(key) : null;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
    /**
     * key,value存入缓存,和map.put用法一样
     * @param key key
     * @param value value
     * @author lilun
     * @date 2018-12-20 10:13:18
     */
    public void put(K key, V value) {
        try {
            readWriteLock.writeLock().lock();
            this.cache.put(key, value);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
    /**
     * 清理缓存中的所用数据,和map.clear一样
     * @author lilun
     * @date 2018-12-20 10:13:18
     */
    public void reset() {
        try {
            readWriteLock.writeLock().lock();
            this.cache.clear();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public String toString(){
        return JSONObject.toJSONString(this.cache);
    }
    public static void main(String[] args) {
        CacheMap<Integer,Integer> cache = new CacheMap<Integer,Integer>(6);
        for(int i=0; i<10; i++){
            cache.put(new Integer(i), new Integer(i));
        }
        System.out.println(cache);
        cache.get(6);
        System.out.println(cache);
        cache.reset();
        System.out.println(cache);
    }
}
