package com.funny.study.java.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class BlockQueueProductTest {

    public static final ArrayBlockingQueue QUEUE = new ArrayBlockingQueue(100);

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            Object object = null;
            try {
                object = QUEUE.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (object != null) {
                System.out.println(System.nanoTime() + "consumer:" + object);
            }
        }).start();
        for (; ; ) {
            Thread.sleep(1000);
            QUEUE.add(new Object());
            LockSupport.park();

        }

    }

}
