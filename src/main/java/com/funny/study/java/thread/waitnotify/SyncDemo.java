package com.funny.study.java.thread.waitnotify;

import java.util.concurrent.Executors;

public class SyncDemo {

    public static void main(String[] args) throws InterruptedException {
        final Object object = new Object();


        // 消费者
        Executors.newFixedThreadPool(2).submit(()->{
            while (true){
                synchronized (object){
                    try {
                        System.out.println(Thread.currentThread().getName() + " object wait");
                        object.wait();
                        System.out.println(Thread.currentThread().getName() + " work start");
                        System.out.println(Thread.currentThread().getName() + " work end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread.sleep(1000);
        //生产者
        Executors.newFixedThreadPool(2).submit(()->{
            for(int i = 1;i<10;i++){
                synchronized (object){
                    try {
                        Thread.sleep(3000);
                        object.notifyAll();
                        System.out.println(Thread.currentThread().getName() + " object notifyAll");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread.sleep(200000000);
    }
}
