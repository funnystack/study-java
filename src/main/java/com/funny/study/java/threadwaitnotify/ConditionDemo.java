package com.funny.study.java.threadwaitnotify;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true){
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + " condition await");
                        condition.await(); // wait()是 object类的方法
                        System.out.println(Thread.currentThread().getName() + " work start");
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " work end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }
            }
        };
        // 消费者
        Executors.newFixedThreadPool(1).submit(runnable);
        Executors.newFixedThreadPool(1).submit(runnable);

        Thread.sleep(1000);
        //生产者
        Executors.newFixedThreadPool(2).submit(()->{
            while (true){
                lock.lock();
                condition.signalAll();
                System.out.println(Thread.currentThread().getName() + " condition signalAll");
                lock.unlock();
            }
        });

        Thread.sleep(200000000);
    }
}
