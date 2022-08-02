package com.funny.study.java.container.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ArrayBlockQueueProductTest {

    public static final ArrayBlockingQueue<String> QUEUE = new ArrayBlockingQueue(5);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                String object = null;
                try {
                    object = QUEUE.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":consumer:" + object);
            }
        }, "consumer").start();

        for (int i = 1; i < 10; i++) {
            Integer aa = i;
            new Thread(() -> {
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    QUEUE.add("producer-" + aa + "-" + ThreadLocalRandom.current().nextInt());
                }
            }, "producer-" + aa).start();
        }

        LockSupport.park();
    }

}
