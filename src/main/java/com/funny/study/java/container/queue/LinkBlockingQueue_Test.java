package com.funny.study.java.container.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkBlockingQueue_Test {
    private static final int MAX_CAPACITY = 10;
    private static LinkedBlockingQueue<Object> goods = new LinkedBlockingQueue<Object>(MAX_CAPACITY);

    public static void main(String[] args) {
        (new ProducerThread()).start();

        (new ConsumerThread()).start();
    }

    static class ProducerThread extends Thread {
        public void run() {
            while (true) {
                // 每隔 1000 毫秒生产一个商品
                try {
                    Thread.sleep(1000);

                    goods.put(new Object());
                    System.out.println("Produce goods, total: " + goods.size());
                } catch (InterruptedException e) {
                }
            }
        }
    }

    static class ConsumerThread extends Thread {
        public void run() {
            while (true) {
                // 每隔 500 毫秒消费一个商品
                try {
                    Thread.sleep(500);

                    goods.take();
                    System.out.println("Consume goods, total: " + goods.size());
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
