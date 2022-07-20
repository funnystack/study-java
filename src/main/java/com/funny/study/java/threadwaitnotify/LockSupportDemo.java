package com.funny.study.java.threadwaitnotify;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {

    private static ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();
    private static volatile int state;
    public static void main(String[] args) throws InterruptedException {
        String key = "123";
        //生产者
        Executors.newFixedThreadPool(2).submit(new ZkNotify(key,Thread.currentThread()));
        System.out.println("wait ......begin ");
        long start = System.currentTimeMillis();
        LockSupport.park();
        System.out.println("wait ......end ");
        System.out.println(map.get(key) + ",use:" + (System.currentTimeMillis()-start));
    }

    static class ZkNotify implements Runnable{
        private String key;
        private Thread thread;

        public ZkNotify(String key, Thread thread) {
            this.key = key;
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key,1);
            LockSupport.unpark(thread);
        }
    }
}
